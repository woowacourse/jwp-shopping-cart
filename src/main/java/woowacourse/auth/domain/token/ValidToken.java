package woowacourse.auth.domain.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import woowacourse.common.exception.AuthenticationException;

public class ValidToken implements Token {

    private final String value;

    public ValidToken(String value) {
        this.value = value;
    }

    public String getPayload(SecretKey tokenKey) {
        try {
            return extractClaims(tokenKey).getSubject();
        } catch (ExpiredJwtException e) {
            throw AuthenticationException.ofInvalidToken();
        } catch (JwtException | IllegalArgumentException e) {
            throw AuthenticationException.ofUnauthenticated();
        }
    }

    private Claims extractClaims(SecretKey tokenKey) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenKey)
                .build()
                .parseClaimsJws(value)
                .getBody();
    }

    public String getValue() {
        return value;
    }
}
