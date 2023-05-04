package cart.mvcconfig;

import cart.auth.infrastructure.BasicAuthorizationExtractor;
import cart.dto.UserAuthInfo;
import cart.exception.UserAuthentificationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAuthInterceptor implements HandlerInterceptor {
    private final BasicAuthorizationExtractor basicAuthorizationExtractor;

    public UserAuthInterceptor(BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserAuthInfo userAuthInfo = basicAuthorizationExtractor.extract(request);
        if (userAuthInfo == null) {
            throw new UserAuthentificationException("사용자 권한이 없습니다");
        }
        return true;
    }
}
