package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.PasswordRequest;
import woowacourse.shoppingcart.dto.response.UserNameDuplicationResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
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
        Customer actual = customerDao.getCustomerByUserName(customerRequest1.getUsername());
        assertThat(actual.getUserName()).isEqualTo(customerRequest1.getUsername());
    }

    @DisplayName("중복된 회원을 가입시키려할 때 예외를 발생시킨다.")
    @Test
    void addCustomer_duplicated() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customerService.addCustomer(customerRequest2))
                .withMessageContaining("중복");
    }

    @ParameterizedTest
    @DisplayName("중복 여부를 검사한다.")
    @CsvSource({"forky, true", "kth990303, false"})
    void checkDuplication(String username, boolean expected) {
        customerService.addCustomer(customerRequest1);
        UserNameDuplicationResponse response = customerService.checkDuplication(username);
        assertThat(response.getIsUnique()).isEqualTo(expected);
    }

    @DisplayName("id값으로 회원을 찾는다.")
    @Test
    void getCustomer() {
        customerService.addCustomer(customerRequest1);
        CustomerResponse actual = customerService.getCustomer(customerRequest1.getUsername());
        assertThat(actual.getUsername()).isEqualTo(customerRequest1.getUsername());
    }

    @DisplayName("비밀번호를 성공적으로 변경한다.")
    @Test
    void updatePassword() {
        customerService.addCustomer(customerRequest1);
        Customer customer = Customer.of(customerRequest1.getUsername(), customerRequest1.getPassword(),
                customerRequest1.getNickname(), customerRequest1.getAge());

        String newPassword = "forky@forky123";
        PasswordRequest passwordRequest = new PasswordRequest(customer.getPassword(), newPassword);
        customerService.updatePassword(customer.getUserName(), passwordRequest);

        Customer actual = customerDao.getCustomerByUserName(customerRequest1.getUsername());
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @DisplayName("비밀번호를 제외한 회원 정보를 성공적으로 변경한다.")
    @Test
    void updateInfo() {
        customerService.addCustomer(customerRequest1);
        String newNickName = "김태현";
        int newAge = 27;
        Customer originCustomer = Customer.of(customerRequest1.getUsername(), customerRequest1.getPassword(),
                customerRequest1.getNickname(), customerRequest1.getAge());
        CustomerRequest updateCustomer =
                new CustomerRequest(originCustomer.getUserName(), originCustomer.getPassword(), newNickName, newAge);

        customerService.updateInfo(originCustomer.getUserName(), updateCustomer);

        Customer actual = customerDao.getCustomerByUserName(customerRequest1.getUsername());

        assertAll(
                () -> assertThat(actual.getNickName()).isEqualTo(newNickName),
                () -> assertThat(actual.getAge()).isEqualTo(newAge)
        );
    }


    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        customerService.addCustomer(customerRequest1);
        customerService.deleteCustomer(customerRequest1.getUsername());

        assertThatExceptionOfType(InvalidCustomerException.class)
                .isThrownBy(() -> customerDao.getCustomerByUserName(customerRequest1.getUsername()))
                .withMessageContaining("존재");
    }
}
