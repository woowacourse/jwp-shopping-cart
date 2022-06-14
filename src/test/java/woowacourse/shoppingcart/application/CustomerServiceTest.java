package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.HashPasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncodePassword;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.RawPassword;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UsernameDuplicationResponse;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Sql("/test_schema.sql")
class CustomerServiceTest {
    private final CustomerRequest customerRequest1 =
            new CustomerRequest("kth990303", "kth@@123", "케이", 23);
    private final CustomerRequest customerRequest2 =
            new CustomerRequest("puterism", "password123!", "nickname", 24);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerService customerService;

    @DisplayName("회원을 성공적으로 가입시킨다.")
    @Test
    void addCustomer() {
        customerService.addCustomer(customerRequest1);
        Customer actual = customerDao.findCustomerByUsername(customerRequest1.getUsername())
                .orElseThrow(InvalidCustomerException::new);
        assertThat(actual.getUsername()).isEqualTo(customerRequest1.getUsername());
    }

    @DisplayName("중복된 회원을 가입시키려할 때 예외를 발생시킨다.")
    @Test
    void addCustomer_duplicated() {
        customerService.addCustomer(customerRequest1);
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> customerService.addCustomer(customerRequest1))
                .withMessageContaining("중복");
    }

    @ParameterizedTest
    @DisplayName("중복 여부를 검사한다.")
    @CsvSource({"forky, true", "kth990303, false"})
    void checkDuplication(String username, boolean expected) {
        customerService.addCustomer(customerRequest1);
        UsernameDuplicationResponse response = customerService.checkDuplication(username);
        assertThat(response.getIsUnique()).isEqualTo(expected);
    }

    @DisplayName("비밀번호를 성공적으로 변경한다.")
    @Test
    void updatePassword() {
        customerService.addCustomer(customerRequest1);
        Customer customer = Customer.of(customerRequest1.getUsername(), encode(customerRequest1.getPassword()),
                customerRequest1.getNickname(), customerRequest1.getAge());

        String newPassword = "forky@forky123";
        PasswordRequest passwordRequest = new PasswordRequest("kth@@123", newPassword);
        customerService.updatePassword(customer, passwordRequest);

        Customer actual = customerDao.findCustomerByUsername(customerRequest1.getUsername())
                .orElseThrow(InvalidCustomerException::new);
        assertThat(actual.getPassword()).isEqualTo(encode(newPassword).getPassword());
    }

    @DisplayName("비밀번호를 변경할 때, 올바르지 않은 기존 비밀번호를 보낼 경우 예외를 발생시킨다.")
    @Test
    void updatePassword_invalidOldPassword() {
        customerService.addCustomer(customerRequest1);
        Customer customer = Customer.of(customerRequest1.getUsername(), encode(customerRequest1.getPassword()),
                customerRequest1.getNickname(), customerRequest1.getAge());

        String newPassword = "forky@forky123";
        PasswordRequest passwordRequest = new PasswordRequest(newPassword, newPassword);
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> customerService.updatePassword(customer, passwordRequest))
                .withMessageContaining("일치");
    }

    @DisplayName("비밀번호를 제외한 회원 정보를 성공적으로 변경한다.")
    @Test
    void updateInfo() {
        customerService.addCustomer(customerRequest1);
        String newNickName = "김태현";
        int newAge = 27;
        Customer originCustomer = Customer.of(customerRequest1.getUsername(), encode(customerRequest1.getPassword()),
                customerRequest1.getNickname(), customerRequest1.getAge());
        CustomerRequest updateCustomer =
                new CustomerRequest(originCustomer.getUsername(), originCustomer.getPassword(), newNickName, newAge);

        customerService.updateInfo(originCustomer, updateCustomer);

        Customer actual = customerDao.findCustomerByUsername(customerRequest1.getUsername())
                .orElseThrow(InvalidCustomerException::new);

        assertAll(
                () -> assertThat(actual.getNickname()).isEqualTo(newNickName),
                () -> assertThat(actual.getAge()).isEqualTo(newAge)
        );
    }

    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        customerService.addCustomer(customerRequest1);

        Customer customer = Customer.of(customerRequest1.getUsername(), encode(customerRequest1.getPassword()),
                customerRequest1.getNickname(), customerRequest1.getAge());
        customerService.deleteCustomer(customer);

        assertThatExceptionOfType(InvalidCustomerException.class)
                .isThrownBy(() -> customerDao.findIdByUsername(customer.getUsername()))
                .withMessageContaining("존재");
    }

    private EncodePassword encode(String rawPassword) {
        RawPassword password = new RawPassword(rawPassword);
        PasswordEncoder passwordEncoder = new HashPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}