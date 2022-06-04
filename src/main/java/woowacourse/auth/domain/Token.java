package woowacourse.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import woowacourse.exception.AuthenticationException;
import woowacourse.exception.ForbiddenException;

public class Token {

    private final String value;

    public Token(String value) {
        this.value = value;
    }

    public String getPayload(SecretKey tokenKey) {
        try {
            return extractClaims(tokenKey).getSubject();
        } catch (ExpiredJwtException e) {
            throw AuthenticationException.ofInvalidToken();
        } catch (JwtException | IllegalArgumentException e) {
            throw new ForbiddenException("로그인이 필요합니다.");
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
