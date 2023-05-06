package cart.config.util;

import static cart.fixture.MemberFixture.TEST_MEMBER1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.exception.AuthTypeInValidException;
import cart.exception.AuthorizationException;
import cart.service.dto.MemberInfo;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

class BasicAuthExtractorTest {

    private static final BasicAuthExtractor BASIC_AUTH_EXTRACTOR = new BasicAuthExtractor();
    private static final String TEST_EMAIL = TEST_MEMBER1.getEmail();
    private static final String TEST_PASSWORD = TEST_MEMBER1.getPassword();

    @Nested
    @DisplayName("http 요청 헤더에서 basicAuth 형태의 credential을 memberInfo로 추출하는 기능 테스트")
    class extractTest {

        @Test
        @DisplayName("정상적으로 MemberInfo가 추출하는 기능 테스트")
        void success() {
            final HttpServletRequest request =
                    makeMockRequest(makeCredential("Basic", TEST_EMAIL, TEST_PASSWORD));

            final MemberInfo extractedMemberInfo = BASIC_AUTH_EXTRACTOR.extract(request);

            assertAll(
                    () -> assertThat(extractedMemberInfo.getEmail())
                            .isEqualTo(TEST_EMAIL),
                    () -> assertThat(extractedMemberInfo.getPassword())
                            .isEqualTo(TEST_PASSWORD)
            );
        }

        @ParameterizedTest
        @DisplayName("인증 타입이 Basic이 아닌 다른 케이스인 경우")
        @ValueSource(strings = {"Barer", "Digest"})
        void InvalidAuthTypeTest(final String principal) {
            final HttpServletRequest request = makeMockRequest(makeCredential(principal, TEST_EMAIL, TEST_PASSWORD));

            assertThatThrownBy(() -> BASIC_AUTH_EXTRACTOR.extract(request))
                    .isInstanceOf(AuthTypeInValidException.class);
        }

        @Test
        @DisplayName("Authorization Header가 없는 경우")
        void authorizationHeaderIsNullTest() {
            final HttpServletRequest request = makeMockRequest(null);

            assertThatThrownBy(() -> BASIC_AUTH_EXTRACTOR.extract(request))
                    .isInstanceOf(AuthorizationException.class);
        }
    }

    private static HttpServletRequest makeMockRequest(final String headerValue) {
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getHeader(any())).thenReturn(headerValue);
        return request;
    }

    private static String makeCredential(final String principal, final String email, final String password) {
        return principal + " " + encodeBase64(email + ":" + password);
    }

    private static String encodeBase64(final String value) {
        final byte[] bytes = Base64.encodeBase64(value.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }
}
