package cart.authentication;

import cart.dto.AuthInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authInfo = authorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new AuthorizationException("사용자의 권한이 없습니다.");
        }
        return super.preHandle(request, response, handler);
    }
}
