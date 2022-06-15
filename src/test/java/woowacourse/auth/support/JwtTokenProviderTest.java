package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String SECRET_KEY_EXAMPLE = "7KuM7LmY64qU7J6l67CU6rWs64uI7YyA7JeQ7ISc66ek7Jqw7Iic7ZWt7KSR7J247YG066CI7J207JmA7I2s6rO8652865287YyA7J24642w7KeA6riI7IK07Ked7LaU65297KSR7J246rKD6rCZ6riw64+E7ZWY6rOg7ZWY7KeA66eM7Lmo66qw65CY66m07JWI65CY64uI6rmQ7Je07Ius7Z6I7ZW07JW87KeV";
    private static final JwtTokenProvider TOKEN_PROVIDER = new JwtTokenProvider(SECRET_KEY_EXAMPLE, 3600000);

    @Test
    @DisplayName("문자열을 토큰으로 만든다.")
    void createToken() {
        // given
        final String id = "1";

        // when
        final String token = TOKEN_PROVIDER.createToken(id);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(TOKEN_PROVIDER.getPayload(token)).isEqualTo(id)
        );
    }

    @Test
    @DisplayName("토큰 유효성을 검사한다.")
    void validateToken() {
        // given
        final String id = "1";

        // when
        final String token = TOKEN_PROVIDER.createToken(id);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(TOKEN_PROVIDER.validateToken(token)).isTrue()
        );
    }

    @Test
    @DisplayName("토큰 유효성을 검사한다.")
    void validateToken_invalidToken_returnFalse() {
        // when, then
        assertThat(TOKEN_PROVIDER.validateToken("token")).isFalse();
    }
}
