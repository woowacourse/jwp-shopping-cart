package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayName("JWT 기능 관련")
class JwtTokenProviderTest {

    private static final JwtTokenProvider PROVIDER = new JwtTokenProvider("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno", 60000);

    @Test
    void JWT_토큰_발급_후_페이로드_확인() {
        String token = PROVIDER.createToken("하이");

        String payload = PROVIDER.getPayload(token);

        assertThat(payload).isEqualTo("하이");
    }

    @Test
    void 토큰_유효성_검증() {
        String token = PROVIDER.createToken("하이");

        PROVIDER.validateToken(token);
    }
}
