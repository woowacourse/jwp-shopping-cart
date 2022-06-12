package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
class AuthControllerTest {

    private final AuthController authController;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthControllerTest(AuthController authController, JwtTokenProvider jwtTokenProvider) {
        this.authController = authController;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    void login() {
        TokenRequest tokenRequest = new TokenRequest("email@email.com", "password123!");

        ResponseEntity<TokenResponse> response = authController.login(tokenRequest);

        HttpStatus statusCode = response.getStatusCode();
        String accessToken = Objects.requireNonNull(response.getBody()).getToken();
        String payload = jwtTokenProvider.getSubject(accessToken);

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.OK),
                () -> assertThat(payload).isEqualTo(tokenRequest.getEmail())
        );
    }

    @DisplayName("잘못된 이메일과 비밀번호를 입력한 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, invalid123!", "notexistingemail@email.com, password123!",
            "notexistingemail@email.com, invalid123!"})
    void loginFail(final String email, final String password) {
        TokenRequest tokenRequest = new TokenRequest(email, password);

        assertThatThrownBy(() -> authController.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("아이디나 비밀번호를 잘못 입력했습니다.");
    }
}
