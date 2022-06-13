package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql("/init.sql")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Test
    void 회원가입() {
        var signUpRequest = new SignUpRequest("alpha", "bcc0830@naver.com", "012345");

        var signUpResponse = customerService.signUp(signUpRequest);

        assertAll(
                () -> assertThat(signUpResponse.getUsername()).isEqualTo("alpha"),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo("bcc0830@naver.com")
        );
    }

    @Test
    void 중복된_이름으로_회원가입을_하는_경우() {
        var signUpRequest = new SignUpRequest("puterism", "crew10@naver.com", "012345");

        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("[ERROR] 이미 존재하는 사용자 이름입니다.");
    }

    @Test
    void 중복된_이메일로_회원가입을_하는_경우() {
        var signUpRequest = new SignUpRequest("chicChoc", "crew01@naver.com", "012345");

        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("[ERROR] 이미 존재하는 이메일입니다.");
    }

    @Test
    void 비밀번호를_수정하는_경우() {
        var username = "puterism";
        var authorizedCustomer = new AuthorizedCustomer(1L, username, "crew01@naver.com", "a12345");
        var changePasswordRequest = new ChangePasswordRequest("a12345", "a123456");

        customerService.changePassword(authorizedCustomer, changePasswordRequest);

        var customer = customerDao.findCustomerByUserName(username);

        assertThat(customer.getPassword()).isEqualTo("a123456");
    }

    @Test
    void 비밀번호를_수정할때_현재_비밀번호가_일치하지_않는_경우() {
        var username = "puterism";
        var authorizedCustomer = new AuthorizedCustomer(1L, username, "crew01@naver.com", "a12345");
        var changePasswordRequest = new ChangePasswordRequest("a1234567", "a123456");

        assertThatThrownBy(
                () -> customerService.changePassword(authorizedCustomer, changePasswordRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @Test
    void 회원탈퇴시_현재_비밀번호가_일치하지_않는_경우() {
        var username = "puterism";
        var authorizedCustomer = new AuthorizedCustomer(1L, username, "crew01@naver.com", "a12345");
        var deleteCustomerRequest = new DeleteCustomerRequest("1231");

        assertThatThrownBy(() -> customerService.deleteUser(authorizedCustomer, deleteCustomerRequest)).isInstanceOf(
                InvalidCustomerException.class);
    }

    @Test
    void 회원탈퇴() {
        String username = "puterism";
        var authorizedCustomer = new AuthorizedCustomer(1L, username, "crew01@naver.com", "a12345");
        var deleteCustomerRequest = new DeleteCustomerRequest("a12345");

        customerService.deleteUser(authorizedCustomer, deleteCustomerRequest);

        assertThat(customerDao.isExistName(username)).isFalse();
    }
}
