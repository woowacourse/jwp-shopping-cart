package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String KEY_STRING = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno";
    private final JwtTokenProvider provider = new JwtTokenProvider(new JwtAttribute(KEY_STRING, 1000L));

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        final String payload = "hello-world";
        // when
        final String token = provider.createToken(payload);
        // then
        assertThat(token).isNotNull();
    }

    @DisplayName("Payload를 복호화한다.")
    @Test
    void getPayload() {
        // given
        final String payload = "hello-world";
        final String token = provider.createToken(payload);

        // when
        final String decoded = provider.getPayload(token);
        // then

        assertThat(decoded).isEqualTo(payload);
    }

    @DisplayName("유효한 토큰인지 확인한다.")
    @Test
    void isValid() {
        // given
        final String payload = "hello-world";
        final String token = provider.createToken(payload);
        // when
        final boolean isValid = provider.isValid(token);
        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("유효한 토큰이 아니라면 예외를 던진다.")
    @Test
    void throwsExceptionWithInvalidToken() {
        // given
        String invalidToken = "Bearer aaaaaaaaa.bbbbbbbb.cccccc";
        // when
        final boolean isValid = provider.isValid(invalidToken);
        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("시간이 지나 유효하지 않은 토큰이라면 예외를 던진다.")
    @Test
    void throwsExceptionOnTimeOver() {
        // given
        JwtTokenProvider timeoutProvider = new JwtTokenProvider(new JwtAttribute(KEY_STRING, -1L));
        final String token = timeoutProvider.createToken("payload");
        // when
        final boolean isValid = provider.isValid(token);
        // then
        assertThat(isValid).isFalse();
    }
}