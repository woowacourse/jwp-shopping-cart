package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;

public class AuthInterceptor implements HandlerInterceptor {

    private AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreflight(request)) {
            return true;
        }
        String token = AuthorizationExtractor.extract(request);
        validateExpiredToken(token);
        return true;
    }

    private boolean isPreflight(HttpServletRequest httpServletRequest) {
        return HttpMethod.OPTIONS.matches(httpServletRequest.getMethod());
    }

    private void validateExpiredToken(String token) {
        if (!authService.isValidToken(token)) {
            throw new InvalidTokenException();
        }
    }
}
