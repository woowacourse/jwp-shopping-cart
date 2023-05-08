package cart.global.annotation;

import cart.auth.AuthAccount;
import cart.global.exception.auth.InvalidAuthorizationTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInArgumentResolverCompositeTest {

    private static final String EMAIL = "example@example.com";
    private static final String PASSWORD = "password";

    private final LogInArgumentResolverComposite logInArgumentResolverComposite = new LogInArgumentResolverComposite();
    private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    private final NativeWebRequest nativeWebRequest = new ServletWebRequest(mockHttpServletRequest);


    @Test
    @DisplayName("resolveArgument() : 지원하지 않는 인증 형식이 아니면 InvalidAuthorizationTypeException가 발생한다.")
    void test_resolveArgument_InvalidAuthorizationTypeException() throws Exception {
        //given
        final String credentials = EMAIL + ":" + PASSWORD;
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, credentials);

        //when & then
        assertThatThrownBy(
                () -> logInArgumentResolverComposite.resolveArgument(
                        null,
                        null,
                        nativeWebRequest,
                        null
                )
        ).isInstanceOf(InvalidAuthorizationTypeException.class);
    }

    @Test
    @DisplayName("resolveArgument() : Basic 인증을 사용하여 사용자 정보를 반환할 수 있다.")
    void test_resolveArgument_basic() throws Exception {
        //given
        final String credentials = EMAIL + ":" + PASSWORD;
        final String basicAuth = encodingBase64(credentials);

        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, basicAuth);

        //when
        final AuthAccount authAccount = (AuthAccount) logInArgumentResolverComposite.resolveArgument(
                null,
                null,
                nativeWebRequest,
                null
        );

        //then
        assertAll(
                () -> assertEquals(EMAIL, authAccount.getEmail()),
                () -> assertEquals(PASSWORD, authAccount.getPassword())
        );
    }

    private String encodingBase64(final String credentials) {
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    @Test
    @DisplayName("resolveArgument() : [아직 지원 X] Bearer 인증을 사용하여 사용자 정보를 반환할 수 있다.")
    void test_resolveArgument_bearer() throws Exception {
        //given
        final String credentials = EMAIL + ":" + PASSWORD;
        final String bearerAuth = "Bearer " + credentials;

        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, bearerAuth);

        //when & then
        assertThatThrownBy(
                () -> logInArgumentResolverComposite.resolveArgument(
                        null,
                        null,
                        nativeWebRequest,
                        null
                )
        ).isInstanceOf(UnsupportedOperationException.class);
    }
}
