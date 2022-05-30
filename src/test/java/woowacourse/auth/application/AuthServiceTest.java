package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AuthServiceTest {

    private final AuthService authService;

    AuthServiceTest(AuthService authService) {
        this.authService = authService;
    }

    @DisplayName("로그인 정보를 기반으로 토큰을 발급 받는다.")
    @Test
    void createToken() {
        TokenRequest tokenRequest = new TokenRequest("test", "1234567890");

        TokenResponse tokenResponse = authService.createToken(tokenRequest);

        assertThat(tokenResponse.getAccessToken()).isNotEmpty();
    }

}
