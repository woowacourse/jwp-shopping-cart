package woowacourse.auth.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private final SecretKeyProvider secretKeyProvider;

    public JwtTokenProvider(final SecretKeyProvider secretKeyProvider) {
        this.secretKeyProvider = secretKeyProvider;
    }

    public String createToken(final String payload) {
        final Claims claims = Jwts.claims().setSubject(payload);
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKeyProvider.generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token) {
        return getClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(final String token) {
        try {
            final Jws<Claims> claims = getClaimsJws(token);
            return !claims
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaimsJws(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyProvider.generateKey())
                .build()
                .parseClaimsJws(token);
    }
}

