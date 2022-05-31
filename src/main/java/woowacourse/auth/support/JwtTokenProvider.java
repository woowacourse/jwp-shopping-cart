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
import woowacourse.auth.application.AuthorizationException;
import woowacourse.auth.application.ForbiddenException;

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
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(toKey())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (DecodingException e) {
            throw new ForbiddenException("유효하지 않은 토큰입니다.");
        }
        validateExpiration(claims);
        return claims.getSubject();
    }

    private void validateExpiration(Claims claims) {
        if (claims.getExpiration().before(new Date())) {
            throw new AuthorizationException("인증기간이 만료되었습니다.");
        }
    }

    private SecretKey toKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
