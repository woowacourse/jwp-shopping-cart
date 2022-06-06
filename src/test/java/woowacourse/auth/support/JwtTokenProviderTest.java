package woowacourse.auth.support;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider;

    JwtTokenProviderTest(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DisplayName("username을 입력하면 토큰을 반환한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> jwtTokenProvider.createToken(YAHO_USERNAME));
    }

    @DisplayName("token을 입력하면 payload를 반환한다.")
    @Test
    void getPayLoad() {
        String token = jwtTokenProvider.createToken(YAHO_USERNAME);
        String payload = jwtTokenProvider.getPayload(token);
        assertThat(payload).isEqualTo(YAHO_USERNAME);
    }

    @DisplayName("유효한 token이면 true를 반환한다.")
    @Test
    void validateToken_valid() {
        String token = jwtTokenProvider.createToken(YAHO_USERNAME);
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertThat(isValid).isEqualTo(true);
    }

    @DisplayName("유효하지 않음 token이면 false를 반환한다.")
    @Test
    void validateToken_notValid() {
        boolean isValidate = jwtTokenProvider.validateToken("안녕.나는.토큰");
        assertThat(isValidate).isEqualTo(false);
    }
}
