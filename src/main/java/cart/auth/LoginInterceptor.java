package cart.auth;
import cart.service.AuthService;
import cart.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
        System.out.println(authInfo.getEmail());
        authService.validateLogin(authInfo.getEmail(), authInfo.getPassword());
        System.out.println("login success!!!!!");

        return super.preHandle(request, response, handler);
    }
}
