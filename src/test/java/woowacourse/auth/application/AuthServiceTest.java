package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;

@SpringBootTest
class AuthServiceTest {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceTest(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DisplayName("토큰을 만들어 반환한다.")
    @Test
    void createToken() {
        TokenRequest tokenRequest = new TokenRequest("email@email.com", "password123!");

        String accessToken = authService.createToken(tokenRequest);

        assertThat(jwtTokenProvider.getPayload(accessToken)).isEqualTo("email@email.com");
    }
}
