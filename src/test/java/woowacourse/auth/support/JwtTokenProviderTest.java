package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import woowacourse.common.exception.UnauthorizedException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayName("JWT 기능 관련")
class JwtTokenProviderTest {

    private static final JwtTokenProvider PROVIDER = new JwtTokenProvider(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
            60000);

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

    @Test
    void 토큰_만료기간_검증() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
                0);
        String token = jwtTokenProvider.createToken("하이");

        assertThatThrownBy(() -> jwtTokenProvider.validateToken(token))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("만료된 토큰입니다.");
    }

    @Test
    void 올바르지_않은_시그니쳐로_토큰_검증() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
                60000);
        String token = PROVIDER.createToken("하이");

        assertThatThrownBy(() -> jwtTokenProvider.validateToken(token))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @Test
    void 유효하지_않은_토큰_검증() {
        String token = "jklfjlkfalkfael";

        assertThatThrownBy(() -> PROVIDER.validateToken(token))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }
}
