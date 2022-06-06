package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtTokenProviderTest(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Test
    void createToken() {
        // given
        String payload = "1";
        final String token = jwtTokenProvider.createToken(payload);
        // when

        // then
        assertThat(token).contains(".");
    }
}
