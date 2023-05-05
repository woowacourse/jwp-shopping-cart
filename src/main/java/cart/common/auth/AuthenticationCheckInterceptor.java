package cart.common.auth;

import cart.domain.auth.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationCheckInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthService authService;

    public AuthenticationCheckInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) {
        final String authenticationHeader = request.getHeader(AUTHORIZATION);
        if (authenticationHeader == null) {
            throw new AuthenticationException("인증 실패");
        }
        authService.checkAuthenticationHeader(authenticationHeader);
        return true;
    }
}
