package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.LoginException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class CustomerServiceTest {

    private final String email = "test@gmail.com";
    private final String password = "password0!";
    private final String username = "루나";
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDao customerDao;

    @DisplayName("회원가입")
    @Test
    void register() {
        // when
        Customer customer = customerService.register(email, password, username);

        // then
        assertAll(
                () -> assertThat(customer.getEmail()).isEqualTo(email),
                () -> assertThat(customer.getUsername()).isEqualTo(username)
        );
    }

    @DisplayName("비밀번호 수정")
    @Test
    void changePassword() {
        // given
        Customer customer = customerService.register(email, password, username);

        // when
        String newPassword = "newPwd1!";
        customerService.changePassword(customer, password, newPassword);

        // then
        Customer updatedCustomer = customerDao.findByEmail(email);
        Password updatedPassword = updatedCustomer.getPassword();
        assertThat(updatedPassword).isEqualTo(Password.ofWithEncryption(newPassword));
    }

    @DisplayName("회원 일반 정보 수정")
    @Test
    void changeGeneralInfo() {
        // given
        Customer customer = customerService.register(email, password, username);

        // when
        String newUsername = "루나2";
        customerService.changeGeneralInfo(customer, newUsername);

        // then
        Customer updatedCustomer = customerDao.findByEmail(email);
        assertThat(updatedCustomer.getUsername()).isEqualTo(newUsername);
    }

    @DisplayName("회원탈퇴")
    @Test
    void delete() {
        // given
        Customer customer = customerService.register(email, password, username);

        // when
        customerService.delete(customer, password);

        // then
        assertThatThrownBy(() -> customerDao.findByEmail(email))
                .isInstanceOf(LoginException.class);
    }
}
