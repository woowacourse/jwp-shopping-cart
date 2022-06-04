package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String INVALID_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naG8xMDgiLCJpYXQiOjE2NTM5MDg0OTgsImV4cCI6MTY1MzkxMjA5OH0.6XAQq1jsqxnn8zMbW9nNcZ4R-BiIyQvLkraocC1aaaa";
    private static final String SECRET_KEY_EXAMPLE = "7KuM7LmY64qU7J6l67CU6rWs64uI7YyA7JeQ7ISc66ek7Jqw7Iic7ZWt7KSR7J247YG066CI7J207JmA7I2s6rO8652865287YyA7J24642w7KeA6riI7IK07Ked7LaU65297KSR7J246rKD6rCZ6riw64+E7ZWY6rOg7ZWY7KeA66eM7Lmo66qw65CY66m07JWI65CY64uI6rmQ7Je07Ius7Z6I7ZW07JW87KeV";
    private static final JwtTokenProvider JWT_TOKEN_PROVIDER = new JwtTokenProvider(SECRET_KEY_EXAMPLE, 3600000);

    @DisplayName("토큰을 생성하고 payload를 올바르게 가져와야 한다.")
    @Test
    void createToken() {
        String username = "username";
        String token = JWT_TOKEN_PROVIDER.createToken(username);

        assertThat(JWT_TOKEN_PROVIDER.getPayload(token)).isEqualTo(username);
    }

    @DisplayName("토큰이 null이면 false를 반환해야 한다.")
    @Test
    void validateNullToken() {
        assertThat(JWT_TOKEN_PROVIDER.validateToken(null)).isFalse();
    }

    @DisplayName("토큰이 만료됐으면 false를 반환해야 한다.")
    @Test
    void validateExpiredToken() {
        // given
        String username = "username";
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY_EXAMPLE, 1);

        // when
        String token = jwtTokenProvider.createToken(username);

        // then
        assertThat(JWT_TOKEN_PROVIDER.validateToken(token)).isFalse();
    }

    @DisplayName("유효하지 않은 토큰이면 false를 반환해야 한다.")
    @Test
    void validateInvalidToken() {
        assertThat(JWT_TOKEN_PROVIDER.validateToken(INVALID_ACCESS_TOKEN)).isFalse();
    }
}
