package woowacourse.member.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("유효한 토큰이면 true를 반환한다.")
    @Test
    void validateToken_True() {
        String token = jwtTokenProvider.createToken(1L);

        boolean actual = jwtTokenProvider.validateToken(token);

        assertThat(actual).isTrue();
    }

    @DisplayName("유효한 토큰이 아니면 false를 반환한다.")
    @Test
    void validateToken_False() {
        String invalidToken = "abc";

        boolean actual = jwtTokenProvider.validateToken(invalidToken);

        assertThat(actual).isFalse();
    }
}
