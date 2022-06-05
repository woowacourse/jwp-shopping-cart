package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        Customer actual = customerDao.getCustomerByUserName(customerRequest1.getUserName());
        assertThat(actual.getUserName()).isEqualTo(customerRequest1.getUserName());
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
    void checkDuplication(String userName, boolean expected) {
        customerService.addCustomer(customerRequest1);
        UserNameDuplicationRequest request = new UserNameDuplicationRequest(userName);
        UserNameDuplicationResponse response = customerService.checkDuplication(request);
        assertThat(response.isUnique()).isEqualTo(expected);
    }

    @DisplayName("비밀번호를 성공적으로 변경한다.")
    @Test
    void updatePassword() {
        customerService.addCustomer(customerRequest1);
        Customer customer = Customer.of(customerRequest1.getUserName(), customerRequest1.getPassword(),
                customerRequest1.getNickName(), customerRequest1.getAge());

        String newPassword = "forky@forky123";
        PasswordRequest passwordRequest = new PasswordRequest(customer.getPassword(), newPassword);
        customerService.updatePassword(customer, passwordRequest);

        Customer actual = customerDao.getCustomerByUserName(customerRequest1.getUserName());
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @DisplayName("비밀번호를 제외한 회원 정보를 성공적으로 변경한다.")
    @Test
    void updateInfo() {
        customerService.addCustomer(customerRequest1);
        String newNickName = "김태현";
        int newAge = 27;
        Customer originCustomer = Customer.of(customerRequest1.getUserName(), customerRequest1.getPassword(),
                customerRequest1.getNickName(), customerRequest1.getAge());
        CustomerRequest updateCustomer =
                new CustomerRequest(originCustomer.getUserName(), originCustomer.getPassword(), newNickName, newAge);

        customerService.updateInfo(originCustomer, updateCustomer);

        Customer actual = customerDao.getCustomerByUserName(customerRequest1.getUserName());

        assertAll(
                () -> assertThat(actual.getNickName()).isEqualTo(newNickName),
                () -> assertThat(actual.getAge()).isEqualTo(newAge)
        );
    }


    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        customerService.addCustomer(customerRequest1);

        Customer customer = Customer.of(customerRequest1.getUserName(), customerRequest1.getPassword(),
                customerRequest1.getNickName(), customerRequest1.getAge());
        customerService.deleteCustomer(customer);

        assertThatExceptionOfType(InvalidCustomerException.class)
                .isThrownBy(() -> customerDao.getCustomerByUserName(customer.getUserName()))
                .withMessageContaining("존재");
    }
}
