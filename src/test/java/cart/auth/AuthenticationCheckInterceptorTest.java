package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationCheckInterceptorTest {

    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthenticationCheckInterceptor authenticationCheckInterceptor;

    @Test
    @DisplayName("사용자를 인증한다.")
    public void testPreHandle() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        //when
        when(request.getHeader("Authorization"))
            .thenReturn("Authorization");
        when(authService.checkAuthenticationHeader(anyString()))
            .thenReturn(any());
        final boolean result = authenticationCheckInterceptor.preHandle(request, response,
            response);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Authorization header가 없는 상황에 사용자를 인증 실패한다.")
    public void testPreHandleWithoutHeader() {
        //given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        //when
        when(request.getHeader("Authorization"))
            .thenReturn(null);

        //then
        assertThatThrownBy(
            () -> authenticationCheckInterceptor.preHandle(request, response, response))
            .isInstanceOf(AuthenticationException.class);
    }
}
