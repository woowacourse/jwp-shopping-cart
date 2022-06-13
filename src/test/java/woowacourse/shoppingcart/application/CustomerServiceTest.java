package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerRepository;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.NoSuchCustomerException;

@SpringBootTest
@Sql(value = "/sql/ClearTableCustomer.sql")
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerDao;

    @Test
    @DisplayName("회원을 저장하고 회원 정보를 반환한다.")
    void addCustomer() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "12345678");

        // when
        SignUpResponse signUpResponse = customerService.addCustomer(signUpRequest);

        // then
        assertAll(
                () -> assertThat(signUpResponse.getUsername()).isEqualTo(name),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo(email)
        );
    }

    @Test
    @DisplayName("중복된 username으로 회원 등록을 할 수 없다.")
    void addCustomerDuplicateUsername() {
        String name = "greenlawn";
        String email1 = "green@woowa.com";
        String email2 = "rennon@woowa.com";
        SignUpRequest signUpRequest1 = new SignUpRequest(name, email1, "12345678");
        SignUpRequest signUpRequest2 = new SignUpRequest(name, email2, "12345678");
        customerService.addCustomer(signUpRequest1);

        // when
        // then
        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest2))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessageContaining("같은 username이 이미 있습니다.");
    }

    @Test
    @DisplayName("중복된 email으로 회원 등록을 할 수 없다.")
    void addCustomerDuplicateEmail() {
        // given
        String name1 = "greenlawn";
        String name2 = "rennon";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest1 = new SignUpRequest(name1, email, "12345678");
        SignUpRequest signUpRequest2 = new SignUpRequest(name2, email, "12345678");
        customerService.addCustomer(signUpRequest1);

        boolean b1 = customerDao.existByUsername(name1);
        boolean b2 = customerDao.existByUsername(name2);

        // when
        // then
        assertThatThrownBy(() -> customerService.addCustomer(signUpRequest2))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining("같은 Email이 이미 있습니다.");
    }

    @Test
    @DisplayName("나의 정보를 반환한다.")
    void findMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "12345678");
        customerService.addCustomer(signUpRequest);

        // when
        CustomerResponse response = customerService.findMe(name);

        // then
        assertThat(response.getUsername()).isEqualTo("greenlawn");
    }

    @Test
    @DisplayName("나의 정보를 수정한다.")
    void updateMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "12345678");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.updateMe(name, new UpdatePasswordRequest("12345678", "56781234"));

        // then
        assertThat(customerDao.isValidPasswordByUsername(name, "56781234")).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 나의 정보를 수정할 수 없다.")
    void updateMeThrowException() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "12345678");
        customerService.addCustomer(signUpRequest);

        // when & then
        assertThatThrownBy(() ->
                customerService.updateMe(name, new UpdatePasswordRequest("1235", "56781234")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void deleteMe() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "12345678");
        customerService.addCustomer(signUpRequest);

        // when
        customerService.deleteMe(name, new DeleteCustomerRequest("12345678"));

        // given
        assertThatThrownBy(() -> customerDao.findByUsername(name))
                .isInstanceOf(NoSuchCustomerException.class);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 회원을 탈퇴할 수 없다.")
    void deleteMeThrowException() {
        // given
        String name = "greenlawn";
        String email = "green@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(name, email, "12345678");
        customerService.addCustomer(signUpRequest);

        // when
        // given
        assertThatThrownBy(() -> customerService.deleteMe(name, new DeleteCustomerRequest("56781234")))
                .isInstanceOf(InvalidPasswordException.class);
    }
}
