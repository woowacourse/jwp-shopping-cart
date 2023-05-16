package cart.authentication;

import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authInfo = authorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new AuthorizationException("사용자 인증에 실패했습니다.");
        }
        return super.preHandle(request, response, handler);
    }

}
