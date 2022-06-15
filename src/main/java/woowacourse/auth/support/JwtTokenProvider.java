package woowacourse.auth.support;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woowacourse.exception.unauthorization.TokenInvalidException;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                            @Value("${security.jwt.token.expire-length}") long validityInMilliseconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(Long payload) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(Map.of("id", payload))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public long getPayload(String token) {
        try {
            return Long.parseLong(String.valueOf(Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id")));
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidException();
        }
    }
}

