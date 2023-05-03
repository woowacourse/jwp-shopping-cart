package cart.common;

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
        authService.checkAuthenticationHeader(request.getHeader(AUTHORIZATION));
        return true;
    }
}
