package cart.auth;

import cart.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final AuthService authService;
    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;

    public LoginInterceptor(AuthService authService, AuthorizationExtractor<AuthInfo> authorizationExtractor) {
        this.authService = authService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthInfo authInfo = authorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new AuthenticationException(ErrorCode.INVALID_AUTH_HEADER.getMessage());
        }
        authService.validateLogin(authInfo.getEmail(), authInfo.getPassword());

        return true;
    }
}
