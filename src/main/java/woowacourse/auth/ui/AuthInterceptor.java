package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.auth.support.AuthorizationExtractor;

public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        if (isPreflight(request)) {
            return true;
        }

        validateToken(request);
        return true;
    }

    public boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS.toString());
    }

    public void validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        if (token == null) {
            throw new AuthorizationException("토큰이 없습니다.");
        }
        authService.validateToken(token);
    }

}
