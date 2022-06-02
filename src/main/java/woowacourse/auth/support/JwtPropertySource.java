package woowacourse.auth.support;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtPropertySource {

    private final SecretKey jwtSecretKey;
    private final long jwtValidity;

    public JwtPropertySource(@Value("${security.jwt.token.secret-key}") String secretKey,
                             @Value("${security.jwt.token.expire-length}") long validityInMilliseconds) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtValidity = validityInMilliseconds;
    }

    public SecretKey getSecretKey() {
        return jwtSecretKey;
    }

    public Date getExpiration(Date now) {
        return new Date(now.getTime() + jwtValidity);
    }
}
