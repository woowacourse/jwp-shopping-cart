package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.Date;

class JwtTokenProviderTest {

    private static final String SECRET_KEY_EXAMPLE = "7KuM7LmY64qU7J6l67CU6rWs64uI7YyA7JeQ7ISc66ek7Jqw7Iic7ZWt7KSR7J247YG066CI7J207JmA7I2s6rO8652865287YyA7J24642w7KeA6riI7IK07Ked7LaU65297KSR7J246rKD6rCZ6riw64+E7ZWY6rOg7ZWY7KeA66eM7Lmo66qw65CY66m07JWI65CY64uI6rmQ7Je07Ius7Z6I7ZW07JW87KeV";
    private static final JwtTokenProvider TOKEN_PROVIDER = new JwtTokenProvider(SECRET_KEY_EXAMPLE, 3600000);
    private static final String ID = "1";

    @Test
    @DisplayName("문자열을 토큰으로 만든다.")
    void createToken() {
        // given, when
        final String token = TOKEN_PROVIDER.createToken(ID);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(TOKEN_PROVIDER.getPayload(token)).isEqualTo(ID)
        );
    }

    @Test
    @DisplayName("유효한 토큰일 경우 true를 반환한다.")
    void validateToken() {
        // given, when
        final String token = TOKEN_PROVIDER.createToken(ID);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(TOKEN_PROVIDER.validateToken(token)).isTrue()
        );
    }

    @Test
    @DisplayName("유효하지 않은 토큰일 경우 false를 반환한다.")
    void validateToken_invalidToken_returnsFalse() {
        // given, when, then
        assertThat(TOKEN_PROVIDER.validateToken("INVALID_TOKEN")).isFalse();
    }

    @Test
    @DisplayName("토큰을 복호화한다.")
    void getPayload() {
        // given
        final String token = Jwts.builder()
                .claim("id", ID)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY_EXAMPLE.getBytes(StandardCharsets.UTF_8)))
                .compact();

        // when, then
        assertThat(TOKEN_PROVIDER.getPayload(token)).isEqualTo(ID);
    }

    @Test
    @DisplayName("만료된 토큰일 경우 false를 반환한다.")
    void validateToken_expired_returnsFalse() {
        // given
        final Date date = new Date();
        final String token = Jwts.builder()
                .claim("id", ID)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() - 1))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY_EXAMPLE.getBytes(StandardCharsets.UTF_8)))
                .compact();

        // when, then
        assertThat(TOKEN_PROVIDER.validateToken(token)).isFalse();
    }
}
