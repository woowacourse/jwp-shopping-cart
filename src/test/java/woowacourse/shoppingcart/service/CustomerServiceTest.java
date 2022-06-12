package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testData.sql"})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;


    @Test
    void 회원가입() {
        var signUpRequest = new SignUpRequest("alpha", "bcc0830@naver.com", "123456");

        var signUpResponse = customerService.signUp(signUpRequest);

        assertAll(
                () -> assertThat(signUpResponse.getUsername()).isEqualTo("alpha"),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo("bcc0830@naver.com")
        );
    }

    @Test
    void 중복된_이름으로_회원가입을_하는_경우() {
        var signUpRequest = new SignUpRequest("puterism", "crew10@naver.com", "123456");

        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("[ERROR] 이미 존재하는 사용자 이름입니다.");
    }

    @Test
    void 중복된_이메일로_회원가입을_하는_경우() {
        var signUpRequest = new SignUpRequest("chicChoc", "crew01@naver.com", "123456");

        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("[ERROR] 이미 존재하는 이메일입니다.");
    }

    @Test
    void 회원정보_조회() {
        var customerResponse = customerService.findCustomerInformation("puterism");

        assertAll(
                () -> assertThat(customerResponse.getUsername()).isEqualTo("puterism"),
                () -> assertThat(customerResponse.getEmail()).isEqualTo("crew01@naver.com")
        );
    }

    @Test
    void 비밀번호를_수정하는_경우() {
        String username = "puterism";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("a12345", "a123456");

        customerService.changePassword(username, changePasswordRequest);
        Customer customer = customerDao.findCustomerByUserName(username);

        assertThat(new Password(customer.getPassword()).isSamePassword(new Password("a123456"))).isTrue();
    }

    @Test
    void 비밀번호를_수정할때_현재_비밀번호가_일치하지_않는_경우() {
        String username = "puterism";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("123456", "a123456");

        assertThatThrownBy(() -> customerService.changePassword(username, changePasswordRequest)).isInstanceOf(
                InvalidCustomerException.class);
    }

    @Test
    void 회원탈퇴시_현재_비밀번호가_일치하지_않는_경우() {
        String username = "puterism";
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123456");

        assertThatThrownBy(() -> customerService.deleteUser(username, deleteCustomerRequest)).isInstanceOf(
                InvalidCustomerException.class);
    }

    @Test
    void 회원탈퇴() {
        String username = "puterism";

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("a12345");
        customerService.deleteUser(username, deleteCustomerRequest);

        assertThat(customerDao.isValidName(username)).isFalse();
    }
}
