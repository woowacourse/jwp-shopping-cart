package woowacourse.shoppingcart.auth.support.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import woowacourse.shoppingcart.auth.support.exception.AuthException;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") final long validityInMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(final String payload) {
        final Claims claims = Jwts.claims().setSubject(payload);
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token) {
        return extractJwtClaims(token).getSubject();
    }

    public boolean validateToken(final String token) {
        try {
            final Claims claims = extractJwtClaims(token);
            return isJwtExpired(claims);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(AuthExceptionCode.INVALID_TOKEN);
        }
    }

    private Claims extractJwtClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isJwtExpired(final Claims claims) {
        return !(claims.getExpiration()).before(new Date());
    }
}

