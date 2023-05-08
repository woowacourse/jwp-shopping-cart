package cart.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.CustomAuthException;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

class AuthenticationPrincipalArgumentResolverTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final AuthenticationPrincipalArgumentResolver resolver = new AuthenticationPrincipalArgumentResolver();
    private final NativeWebRequest nativeWebRequest = new ServletWebRequest(request);

    @DisplayName("헤더에 Authorization 이 없을 때")
    @Test
    void validateHeader() {
        assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                .isInstanceOf(CustomAuthException.class);
    }

    @DisplayName("헤더에 Authorization 은 있지만 null 인 경우")
    @Test
    void validateNullBasicHeaderValue() {
        request.addHeader("Authorization", "");
        assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                .isInstanceOf(CustomAuthException.class);
    }

    @DisplayName("헤더에 Basic 값이 없을 때")
    @Test
    void validateNotExistBasicHeaderValue() {
        String encodedCredentials = new String(Base64.encodeBase64("1:wuga@naver.com:1234".getBytes()));
        String basicCredentials = "wuga " + encodedCredentials;
        request.addHeader("Authorization", basicCredentials);
        assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                .isInstanceOf(CustomAuthException.class);
    }

    @DisplayName("헤더 Basic 테스트")
    @Nested
    class ValidationBasicHeaderValue {

        @DisplayName("Basic 값만 있을 경우")
        @Test
        void validateOnlyBasicHeaderValue() {
            request.addHeader("Authorization", getHeaderValue(""));
            assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                    .isInstanceOf(CustomAuthException.class);
        }

        @DisplayName("Basic 값은 정상적이지만 구분자가 없을 경우")
        @Test
        void validateDelimiterBasicHeaderValue() {
            request.addHeader("Authorization", getHeaderValue("1;wuga@naver.com;1234"));
            assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                    .isInstanceOf(CustomAuthException.class);
        }

        @DisplayName("Basic 값이 있지만 아이디가 없을 경우")
        @Test
        void validateIdBasicHeaderValue() {
            request.addHeader("Authorization", getHeaderValue("wuga@naver.com:1234"));
            assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                    .isInstanceOf(CustomAuthException.class);
        }

        @DisplayName("Basic 값이 있지만 이메일이 없을 경우")
        @Test
        void validateEmailBasicHeaderValue() {
            request.addHeader("Authorization", getHeaderValue("1:1234"));
            assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                    .isInstanceOf(CustomAuthException.class);
        }

        @DisplayName("Basic 값이 있지만 아이디 범위가 유효하지 않을 경우")
        @Test
        void validateIdRangeBasicHeaderValue() {
            request.addHeader("Authorization", getHeaderValue("1000000000000:wuga@naver.com:1234"));
            assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                    .isInstanceOf(CustomAuthException.class);
        }

        @DisplayName("Basic 값이 있지만 이메일이 형식이 맞지 않을 경우")
        @Test
        void validateEmailTypeBasicHeaderValue() {
            request.addHeader("Authorization", getHeaderValue("1:wuga.com:1234"));
            assertThatThrownBy(() -> resolver.resolveArgument(null, null, nativeWebRequest, null))
                    .isInstanceOf(CustomAuthException.class);
        }

    }

    private String getHeaderValue(final String credentials) {
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        return "Basic " + encodedCredentials;
    }

}