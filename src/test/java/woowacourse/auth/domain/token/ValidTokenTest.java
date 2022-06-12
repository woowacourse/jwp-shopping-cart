package woowacourse.auth.domain.token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.common.exception.AuthenticationException;

@SuppressWarnings("NonAsciiCharacters")
class ValidTokenTest {

    private static final String SECRET_KEY = "secret_key_that_is_long_enough_for_security_purpose";
    private static final SecretKey 올바른_토큰_키 = toSecretKey(SECRET_KEY);
    private static final long 토큰_만료기간 = 10000000;

    @DisplayName("getPayload 메서드는 토큰에 담긴 값을 해석하여 반환")
    @Nested
    class GetPayloadTest {

        @Test
        void 토큰을_발급할_때_사용한_키와_동일한_키를_받은_경우_payload_복원_성공() {
            String 원본_데이터 = "asd12!";
            Token token = new ValidToken(generateAccessToken(원본_데이터, 토큰_만료기간));

            String 복원된_데이터 = token.getPayload(올바른_토큰_키);

            assertThat(복원된_데이터).isEqualTo(원본_데이터);
        }

        @Test
        void 서버에서_사용하는_키와_다른_키로_생성한_토큰인_경우_예외_발생() {
            SecretKey 잘못된_키 = toSecretKey("wrong_wrong_wrong_wrong_wrong_wrong_wrong_wrong_wrong_wrong");
            Token token = new ValidToken(generateAccessToken("asd12!", 잘못된_키, 토큰_만료기간));

            assertThatThrownBy(() -> token.getPayload(올바른_토큰_키))
                    .isInstanceOf(AuthenticationException.class);
        }

        @Test
        void JWT_토큰의_형식에_부합하지_않는_값에_대한_해석시도시_예외발생() {
            String JWT_형식에_부합하지_않는_값 = "invalid_payload";
            Token token = new ValidToken(JWT_형식에_부합하지_않는_값);

            assertThatThrownBy(() -> token.getPayload(올바른_토큰_키))
                    .isInstanceOf(AuthenticationException.class);
        }

        @Test
        void 만료된_토큰에_대한_해석시도시_예외발생() throws InterruptedException {
            Token token = new ValidToken(generateAccessToken("asd12!", 1));
            Thread.sleep(100);
            assertThatThrownBy(() -> token.getPayload(올바른_토큰_키))
                    .isInstanceOf(AuthenticationException.class);
        }
    }

    @Test
    void getValue_메서드는_accessToken_원본값을_그대로_반환() {
        String accessToken = generateAccessToken("asd12!", 토큰_만료기간);

        Token token = new ValidToken(accessToken);
        String actual = token.getValue();

        assertThat(actual).isEqualTo(accessToken);
    }

    private String generateAccessToken(String payload, SecretKey secretKey, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    private String generateAccessToken(String payload, long validityInMilliseconds) {
        return generateAccessToken(payload, 올바른_토큰_키, validityInMilliseconds);
    }

    private static SecretKey toSecretKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
}
