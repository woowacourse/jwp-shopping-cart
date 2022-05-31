package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        String accessToken = Objects.requireNonNull(response.getBody()).getAccessToken();
        String payload = jwtTokenProvider.getPayload(accessToken);

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.OK),
                () -> assertThat(payload).isEqualTo(tokenRequest.getEmail())
        );
    }

    @DisplayName("존재하지 않는 이메일을 입력한 경우 로그인에 실패한다.")
    @Test
    void notExistingEmailLogin() {
        TokenRequest tokenRequest = new TokenRequest("invalidemail@email.com", "password123!");

        assertThatThrownBy(() -> authController.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("아이디나 비밀번호를 잘못 입력했습니다.");
    }

    @DisplayName("잘못된 비밀번호를 입력한 경우 로그인에 실패한다.")
    @Test
    void invalidPasswordLogin() {
        TokenRequest tokenRequest = new TokenRequest("email@email.com", "invalidpassword123!");

        assertThatThrownBy(() -> authController.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("아이디나 비밀번호를 잘못 입력했습니다.");
    }
}
