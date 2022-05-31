package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;

public class AuthInterceptor implements HandlerInterceptor {

    private AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request);
        if (!authService.isValidToken(token)) {
            throw new IllegalStateException("[ERROR] 만료된 토큰입니다.");
        }
        return true;
    }
}
