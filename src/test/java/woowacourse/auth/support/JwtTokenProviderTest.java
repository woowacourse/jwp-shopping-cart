package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("토큰을 생성하고 payload 를 추출한다.")
    @Test
    void createToken_AND_getPayload() {
        // given
        String username = "jo";

        // when
        String token = jwtTokenProvider.createToken(username);

        // then
        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(username);
    }

    @DisplayName("토큰이 유효한지 검증한다. - 토큰이 만료되었다면 false 를 반환한다.")
    @Test
    void validateToken_expiredToken() {
        // given
        Date now = new Date();
        Date validity = new Date(now.getTime() - 3600000);
        SecretKey secretKey = Keys.hmacShaKeyFor(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno".getBytes(
                        StandardCharsets.UTF_8));
        String expiredToken = Jwts.builder()
                .setSubject("1")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // when then
        assertThat(jwtTokenProvider.validateToken(expiredToken)).isFalse();
    }

    @DisplayName("토큰이 유효한지 검증한다. - 부적절한 토큰이면 false 를 반환한다.")
    @Test
    void validateToken_invalidToken() {
        // given
        String invalidToken = "invalid token";

        // when then
        assertThat(jwtTokenProvider.validateToken(invalidToken)).isFalse();
    }
}
