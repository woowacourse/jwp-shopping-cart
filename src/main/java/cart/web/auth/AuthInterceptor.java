package cart.web.auth;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BasicAuthorizationExtractor extractor = BasicAuthorizationExtractor.getInstance();
        UserInfo userInfo = extractor.extract(request);

        authService.authenticate(userInfo);

        return super.preHandle(request, response, handler);
    }
}
