package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String SECRETE_KEY = "thisIsTestSecretKey-thisIsTestSecretKey-thisIsTestSecretKey";
    private static final long VALIDITY_IN_MILLISECONDS = 86400000L;

    private static final String SUBJECT = "email@email.com";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRETE_KEY, VALIDITY_IN_MILLISECONDS);

    @Test
    @DisplayName("토큰을 생성할 수 있다.")
    void createToken() {
        assertThat(jwtTokenProvider.createToken("email@email.com")).isNotNull();
    }

    @Test
    @DisplayName("토큰을 복호화할 수 있다.")
    void getPayload() {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRETE_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setSubject(SUBJECT)
                .compact();

        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(SUBJECT);
    }

    @Test
    @DisplayName("정상 토큰 validation")
    void resolveToken() {
        String token = jwtTokenProvider.createToken(SUBJECT);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("이상한 토큰 validation false")
    void resolveTokenErrorToken_false() {
        assertThat(jwtTokenProvider.validateToken("this.is.error")).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰 validation false")
    void resolveTokenExpiredToken_false() {
        String expiredToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRETE_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setSubject(SUBJECT)
                .setExpiration(new Date((new Date()).getTime() - 1))
                .compact();

        assertThat(jwtTokenProvider.validateToken(expiredToken)).isFalse();
    }
}
