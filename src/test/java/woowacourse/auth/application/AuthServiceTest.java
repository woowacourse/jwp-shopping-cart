package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.AuthorizationException;

@SpringBootTest
@Sql(value = "/sql/ClearTableCustomer.sql")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("로그인에 성공한다.")
    void signIn() {
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "12345678"));
        SignInResponse signInResponse = authService.signIn(new SignInRequest("rennon@woowa.com", "12345678"));

        assertThat(signInResponse.getUsername()).isEqualTo("레넌");
        assertThat(signInResponse.getToken()).isNotNull();
    }

    @Test
    @DisplayName("등록된 회원이 없다면 로그인에 실패한다.")
    void signInFailNoSuchCustomer() {
        assertThatThrownBy(() -> authService.signIn(new SignInRequest("rennon@woowa.com", "12345678")))
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining("로그인에 실패했습니다.");
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인에 실패한다.")
    void signInFail() {
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "12345678"));
        authService.signIn(new SignInRequest("rennon@woowa.com", "12345678"));

        assertThatThrownBy(() -> authService.signIn(new SignInRequest("rennon@woowa.com", "56781234")))
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining("로그인에 실패했습니다.");
    }
}
