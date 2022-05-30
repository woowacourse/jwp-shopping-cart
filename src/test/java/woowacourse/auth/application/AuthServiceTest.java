package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        LoginRequest loginRequest = new LoginRequest("dongho108", "password1234");
        TokenResponse token = authService.createToken(loginRequest);

        assertThat(jwtTokenProvider.validateToken(token.getAccessToken())).isTrue();
    }
}