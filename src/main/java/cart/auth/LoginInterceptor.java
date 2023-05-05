package cart.auth;

import cart.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private AuthService authService;

    public LoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    private AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthInfo authInfo = authorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new AuthenticationException(ErrorCode.INVALID_AUTH_HEADER.getMessage());
        }
        authService.validateLogin(authInfo.getEmail(), authInfo.getPassword());

        return super.preHandle(request, response, handler);
    }
}
