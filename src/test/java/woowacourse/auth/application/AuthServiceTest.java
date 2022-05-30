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

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 정보를 이용해서 토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        TokenRequest request = new TokenRequest("puterism@naver.com", "12349053145");
        String token = authService.createToken(request);

        // when
        String email = jwtTokenProvider.getPayload(token);

        // then
        assertThat(email).isEqualTo("puterism@naver.com");
    }
}
