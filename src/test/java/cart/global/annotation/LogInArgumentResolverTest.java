package cart.global.annotation;

import cart.auth.AuthAccount;
import cart.global.exception.auth.InvalidAuthorizationTypeException;
import cart.global.exception.auth.InvalidEmailFormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInArgumentResolverTest {

    private final LogInArgumentResolver logInArgumentResolver = new LogInArgumentResolver();
    private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    private final NativeWebRequest nativeWebRequest = new ServletWebRequest(mockHttpServletRequest);

    private final String email = "example@example.com";
    private final String password = "password";

    @Test
    @DisplayName("resolveArgument() : 인증이 Basic 형식이 아니면 InvalidAuthorizationTypeException가 발생한다.")
    void test_resolveArgument_InvalidAuthorizationTypeException() throws Exception {
        //given
        final String credentials = email + ":" + password;
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, credentials);

        //when & then
        Assertions.assertThatThrownBy(
                () -> logInArgumentResolver.resolveArgument(
                        null,
                        null,
                        nativeWebRequest,
                        null
                )
        ).isInstanceOf(InvalidAuthorizationTypeException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"example@", "example", "example#example.com", "exampl-e@example.com", "e_x@ex"})
    @DisplayName("resolveArgument() : 이메일 형식이 잘못됐을 경우 InvalidEmailFormatException이 발생한다.")
    void test_resolveArgument_InvalidEmailFormatException(final String email) throws Exception {
        //given
        final String credentials = password + ":" + email;
        final String basicAuth = encodingBase64(credentials);

        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, basicAuth);

        //when & then
        Assertions.assertThatThrownBy(
                () -> logInArgumentResolver.resolveArgument(
                        null,
                        null,
                        nativeWebRequest,
                        null
                )
        ).isInstanceOf(InvalidEmailFormatException.class);
    }

    @Test
    @DisplayName("resolveArgument() : 올바른 인증 형태, 이메일을 통해 사용자 정보를 반환할 수 있다.")
    void test_resolveArgument() throws Exception {
        //given
        final String credentials = email + ":" + password;
        final String basicAuth = encodingBase64(credentials);

        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, basicAuth);

        //when
        final AuthAccount authAccount = (AuthAccount) logInArgumentResolver.resolveArgument(
                null,
                null,
                nativeWebRequest,
                null
        );

        //then
        assertAll(
                () -> assertEquals(email, authAccount.getEmail()),
                () -> assertEquals(password, authAccount.getPassword())
        );
    }

    private String encodingBase64(final String credentials) {
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
