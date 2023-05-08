package cart.mvcconfig;

import cart.auth.infrastructure.BasicAuthorizationExtractor;
import cart.auth.service.AuthService;
import cart.dto.UserAuthInfo;
import cart.exception.UserAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserAuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor;

    public UserAuthInterceptor(AuthService authService, BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.authService = authService;
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserAuthInfo userAuthInfo = basicAuthorizationExtractor.extract(request);
        if (userAuthInfo != null && authService.validAuthentication(userAuthInfo)) {
            return true;
        }
        throw new UserAuthenticationException("사용자 정보가 없습니다");
    }
}
