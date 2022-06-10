package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static woowacourse.auth.support.AuthorizationExtractor.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.auth.exception.InvalidTokenException;

class AuthorizationExtractorTest {

    private static final String KEY_STRING = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno";
    private final JwtTokenProvider provider = new JwtTokenProvider(new JwtAttribute(KEY_STRING, 1000L));
    private final AuthorizationExtractor extractor = new AuthorizationExtractor();

    @DisplayName("토큰을 추출한다.")
    @Test
    void extractToken() {
        // given
        final String token = provider.createToken("payload");
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization"))
            .thenReturn(BEARER_TYPE + token);

        // when
        final String createdToken = extractor.extract(request);

        // then
        assertThat(createdToken).isEqualTo(token);
    }

    @DisplayName("Authorization 헤더가 없으면 예외를 발생한다.")
    @Test
    void throwsExceptionWithoutAuthorizationHeader() {
        // given & when
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization"))
            .thenReturn(null);

        // then
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> extractor.extract(request));
    }

    @DisplayName("Bearer로 시작하지 않으면 예외를 던진다.")
    @Test
    void throwsExceptionWithoutStartingWithBearer() {
        // given & when
        final String token = provider.createToken("payload");
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization"))
            .thenReturn(token);

        // then
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> extractor.extract(request));
    }
}