package cart.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";

    private final BasicAuthExtractor basicAuthExtractor;
    private final AuthService authService;

    public BasicAuthInterceptor(final BasicAuthExtractor basicAuthExtractor, final AuthService authService) {
        this.basicAuthExtractor = basicAuthExtractor;
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authorizationValue = request.getHeader(AUTHORIZATION);
        final UserInfo userInfo = basicAuthExtractor.extract(authorizationValue);

        authService.validate(userInfo);
        return true;
    }
}
