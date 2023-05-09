package cart.auth;

import cart.exception.AuthUserDetailNullException;
import cart.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final AuthService authService;
    private final AuthorizationExtractor<AuthUserDetail> authorizationExtractor;

    public LoginInterceptor(AuthService authService, AuthorizationExtractor<AuthUserDetail> authorizationExtractor) {
        this.authService = authService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthUserDetailNullException, UnAuthorizedException {
        AuthUserDetail authUserDetail = authorizationExtractor.extract(request);
        if (authUserDetail == null) {
            throw new AuthUserDetailNullException();
        }
        authService.validateLogin(authUserDetail.getEmail(), authUserDetail.getPassword());

        return true;
    }
}
