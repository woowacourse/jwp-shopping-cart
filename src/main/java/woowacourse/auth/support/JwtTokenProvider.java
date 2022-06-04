package woowacourse.auth.support;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.annotation.PostConstruct;
import woowacourse.auth.exception.InvalidTokenException;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    private Key key;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @PostConstruct
    private void initializeKey() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(final String payload) {
        final Claims claims = Jwts.claims().setSubject(payload);
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token) {
        return extractJwtClaims(token).getSubject();
    }

    public boolean validateToken(final String token) {
        try {
            final Claims claims = extractJwtClaims(token);
            return isJwtExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private Claims extractJwtClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isJwtExpired(final Claims claims) {
        return !(claims.getExpiration()).before(new Date());
    }
}

