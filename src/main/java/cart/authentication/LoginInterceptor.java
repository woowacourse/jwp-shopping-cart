package cart.authentication;

import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
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
            throw new AuthorizationException("권한이 없는 사용자입니다.");
        }

        return super.preHandle(request, response, handler);
    }
}
