package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JwtTokenProvider.class)
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("payload로 token을 생성할 수 있다.")
    @Test
    void createToken() {
        String actual = jwtTokenProvider.createToken("payload");
        assertThat(actual).isNotBlank();
    }

    @DisplayName("생성된 token을 해석해서 payload값을 얻을 수 있다.")
    @Test
    void getPayload() {
        String given = jwtTokenProvider.createToken("payload");
        String actual = jwtTokenProvider.getPayload(given);
        assertThat(actual).isEqualTo("payload");
    }
}
