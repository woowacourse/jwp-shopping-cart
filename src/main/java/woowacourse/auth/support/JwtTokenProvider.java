package woowacourse.auth.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woowacourse.auth.exception.AuthenticationException;
import woowacourse.auth.exception.ForbiddenException;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String createToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(toKey())
                .compact();
    }

    public String getPayload(String token) {
        try {
            Claims claims = extractClaims(token);
            validateExpiration(claims);
            return claims.getSubject();
        } catch (DecodingException e) {
            throw new ForbiddenException("권한이 없습니다.");
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(toKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private void validateExpiration(Claims claims) {
        Date now = new Date();
        if (claims.getExpiration().before(now)) {
            throw new AuthenticationException("다시 로그인해주세요.");
        }
    }

    private SecretKey toKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
