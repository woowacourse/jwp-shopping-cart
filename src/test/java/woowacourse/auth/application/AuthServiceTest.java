package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.LoginFailedException;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AuthServiceTest {

    private final AuthService authService;
    private final CustomerService customerService;

    AuthServiceTest(final CustomerService customerService, final AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @BeforeEach
    void setUp() {
        customerService.registerCustomer(new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwer1234!@#$"));
    }

    @DisplayName("로그인에 성공하면 토큰을 발급한다.")
    @Test
    void login() {
        final TokenRequest tokenRequest = new TokenRequest("guest@woowa.com", "qwer1234!@#$");
        final TokenResponse tokenResponse = authService.login(tokenRequest);

        assertThat(tokenResponse.getAccessToken()).isNotNull();
    }

    @DisplayName("로그인 실패")
    @Nested
    class LoginFailTest {

        @DisplayName("이메일에 해당하는 회원이 존재하지 않을 경우")
        @Test
        void loginWithWrongEmail() {
            assertThatThrownBy(() -> authService.login(new TokenRequest("admin@woowa.com", "qwer1234!@#$")))
                    .isInstanceOf(LoginFailedException.class);
        }

        @DisplayName("비밀번호가 일치하지 않을 경우, 로그인에 실패한다.")
        @Test
        void loginWithWrongPassword() {
            assertThatThrownBy(() -> authService.login(new TokenRequest("guest@woowa.com", "wrongqwe123!@#")))
                    .isInstanceOf(LoginFailedException.class);
        }
    }
}
