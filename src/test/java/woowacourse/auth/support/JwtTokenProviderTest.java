package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.UnauthorizedException;

@DisplayName("JwtTokenProvider 는")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
            3600000);

    @DisplayName("유효한 토큰이 생성된 후 원하는 playload로 변환되는지 검증한다.")
    @Test
    void checkPayloadAfterIssuingToken() {
        final String payload = "\"email\":\"example@example.com\"";
        final String accessToken = jwtTokenProvider.createToken(payload);
        assertThat(jwtTokenProvider.getPayload(accessToken)).isEqualTo(payload);
    }

    @DisplayName("토큰을 검증할 때")
    @Nested
    class TokenValidationTest {

        private JwtTokenProvider invalidJwtTokenProvider = new JwtTokenProvider(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
                1);

        @DisplayName("토큰이 유효하다면 에러를 발생시키지 않는다.")
        @Test
        void validToken() {
            final String payload = "\"email\":\"example@example.com\"";
            final String accessToken = jwtTokenProvider.createToken(payload);
            assertThatNoException().isThrownBy(() -> jwtTokenProvider.validateToken(accessToken));
        }

        @DisplayName("토큰이 만료된 토큰이면 에러를 던진다.")
        @Test
        void expiredToken() {
            final String payload = "\"email\":\"example@example.com\"";
            final String accessToken = invalidJwtTokenProvider.createToken(payload);
            assertThatThrownBy(() -> invalidJwtTokenProvider.validateToken(accessToken))
                    .isInstanceOf(UnauthorizedException.class)
                    .hasMessage("토큰이 만료되었습니다.");
        }

        @DisplayName("토큰이 발급되지 않은 토큰이면 에러를 던진다.")
        @Test
        void invalidToken() {
            final String unissuedAccessToken = "유효하지 않은 토큰";
            assertThatThrownBy(() -> jwtTokenProvider.validateToken(unissuedAccessToken))
                    .isInstanceOf(UnauthorizedException.class)
                    .hasMessage("유효하지 않은 토큰입니다");
        }
    }
}
