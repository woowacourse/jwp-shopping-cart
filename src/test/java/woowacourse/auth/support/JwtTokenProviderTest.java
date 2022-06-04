package woowacourse.auth.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
