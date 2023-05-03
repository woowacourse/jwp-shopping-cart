package cart.authentication;

import cart.dto.AuthInfo;
import cart.infrastructure.AuthorizationExtractor;
import cart.infrastructure.BasicAuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authInfo = authorizationExtractor.extract(request);

        if (authInfo == null) {
            // TODO: exception 만들기
            throw new RuntimeException();
        }

        return super.preHandle(request, response, handler);
    }
}
