package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {
    private static final String SECRET_KEY = "test-key-for-jwtTokenProviderTest-test-key-for-jwtTokenProviderTest";
    private static final long VALIDITY_IN_MILLISECONDS = 36000L;

    private static final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);

    @Test
    @DisplayName("토큰을 생성할 수 있다.")
    void createToken() {
        assertThat(jwtTokenProvider.createToken("test@email.com")).isNotNull();
    }

    @Test
    @DisplayName("토큰을 복호화할 수 있다.")
    void getPayload() {
        //given
        String payload = "test@email.com";
        String token = jwtTokenProvider.createToken(payload);

        //then
        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(payload);
    }

    @Test
    @DisplayName("올바른 토큰일 경우 validate시 truen를 return한다.")
    void validateTokenTrue() {
        //given
        String payload = "test@email.com";
        String token = jwtTokenProvider.createToken(payload);

        //then
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("올바르지 않은 토큰일 경우 validate시 false를 return한다.")
    void validateTokenFalseByWrongToken() {
        assertThat(jwtTokenProvider.validateToken("is.wrong.token")).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰일 경우 validate시 false를 return한다.")
    void validateTokenFalseByExpiredToken() {
        //given
        JwtTokenProvider shortJwtTokenProvider = new JwtTokenProvider(SECRET_KEY, 1);
        String token = shortJwtTokenProvider.createToken("test@email.com");

        //then
        assertThat(JwtTokenProviderTest.jwtTokenProvider.validateToken(token)).isFalse();
    }
}