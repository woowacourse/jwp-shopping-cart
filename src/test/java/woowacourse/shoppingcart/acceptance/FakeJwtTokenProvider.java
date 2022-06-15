package woowacourse.shoppingcart.acceptance;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import woowacourse.auth.support.JwtTokenProvider;

public class FakeJwtTokenProvider extends JwtTokenProvider {

    private final String secretKey;
    private final long validityInMilliseconds;

    public FakeJwtTokenProvider(String secretKey, long validityInMilliseconds) {
        super(secretKey, validityInMilliseconds);
        this.secretKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno";
        this.validityInMilliseconds = 36000000;
    }

    @Override
    public String createToken(String payload) {
        Claims claims = Jwts.claims();
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}
