package woowacourse.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.DecodingException;
import java.util.Date;
import javax.crypto.SecretKey;
import woowacourse.auth.exception.AuthenticationException;
import woowacourse.auth.exception.ForbiddenException;
import woowacourse.auth.support.JwtPropertySource;

public class Token {

    private final SecretKey tokenKey;
    private final String value;

    public Token(String value, SecretKey tokenKey) {
        this.tokenKey = tokenKey;
        this.value = value;
    }

    public static Token of(String payload, JwtPropertySource propertySource) {
        String accessToken = buildAccessToken(payload, propertySource);
        return new Token(accessToken, propertySource.getSecretKey());
    }

    private static String buildAccessToken(String payload, JwtPropertySource jwtPropertySource) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(jwtPropertySource.getExpiration(now))
                .signWith(jwtPropertySource.getSecretKey())
                .compact();
    }

    public String extractPayload() {
        try {
            Claims claims = extractClaims(value);
            validateExpiration(claims);
            return claims.getSubject();
        } catch (DecodingException e) {
            throw new ForbiddenException("권한이 없습니다.");
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenKey)
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

    public String getValue() {
        return value;
    }
}
