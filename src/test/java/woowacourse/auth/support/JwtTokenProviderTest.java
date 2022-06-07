package woowacourse.auth.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰 값 payload의 sub값을 가져올 수 있다.")
    void getPayLoad() {
        // given
        String token = jwtTokenProvider.createToken("1");
        // when
        String payload = jwtTokenProvider.getPayload(token);
        // then
        assertThat(payload).isEqualTo("1");
    }

    @Test
    @DisplayName("토큰 값을 검증할 수 있다.")
    void checkValidate() {
        // given
        String token = jwtTokenProvider.createToken("1");
        // when
        boolean flag = jwtTokenProvider.validateToken(token);
        // then
        assertThat(flag).isEqualTo(true);
    }
}
