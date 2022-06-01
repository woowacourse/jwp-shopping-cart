package woowacourse.auth.support;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woowacourse.exception.auth.TokenInvalidException;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String createToken(Long payload) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(Map.of("id", payload))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public long getPayload(String token) {
        try {
            return Long.parseLong(String.valueOf(Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id")));
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidException();
        }
    }
}

