package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;

public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        validateExpiredToken(token);
        return true;
    }

    private void validateExpiredToken(final String token) {
        if (!authService.isValidToken(token)) {
            throw new InvalidTokenException();
        }
    }
}
