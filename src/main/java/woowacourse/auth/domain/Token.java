package woowacourse.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.DecodingException;
import java.util.Date;
import javax.crypto.SecretKey;
import woowacourse.auth.exception.AuthenticationException;
import woowacourse.auth.exception.ForbiddenException;

public class Token {

    private final String value;

    public Token(String value) {
        this.value = value;
    }

    public String getPayload(SecretKey tokenKey) {
        try {
            Claims claims = extractClaims(tokenKey);
            validateExpiration(claims);
            return claims.getSubject();
        } catch (DecodingException e) {
            throw new ForbiddenException("권한이 없습니다.");
        }
    }

    private Claims extractClaims(SecretKey tokenKey) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenKey)
                .build()
                .parseClaimsJws(value)
                .getBody();
    }

    private void validateExpiration(Claims claims) {
        Date now = new Date();
        if (claims.getExpiration().before(now)) {
            throw new AuthenticationException("다시 로그인해주세요.");
        }
    }

    public String getValue() {
        return value;
    }
}
