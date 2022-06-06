package woowacourse.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woowacourse.auth.domain.token.Token;

@Component
public class JwtTokenService {

    private final SecretKey secretKey;
    private final long validity;

    public JwtTokenService(@Value("${security.jwt.token.secret-key}") String secretKey,
                           @Value("${security.jwt.token.expire-length}") long validityInMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validity = validityInMilliseconds;
    }

    public Token generateToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        String tokenValue = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(getExpiration(now))
                .signWith(secretKey)
                .compact();
        return new Token(tokenValue);
    }

    private Date getExpiration(Date now) {
        return new Date(now.getTime() + validity);
    }

    public String extractPayload(Token token) {
        return token.getPayload(secretKey);
    }
}
