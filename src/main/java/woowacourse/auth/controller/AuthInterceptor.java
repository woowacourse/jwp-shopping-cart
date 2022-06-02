package woowacourse.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.service.AuthService;
import woowacourse.shoppingcart.exception.auth.AuthorizationException;
import woowacourse.auth.support.AuthorizationExtractor;

public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (isPreflight(request)) {
            return true;
        }
        validateToken(request);
        return true;
    }

    public boolean isPreflight(final HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS.toString());
    }

    public void validateToken(final HttpServletRequest request) {
        final String token = AuthorizationExtractor.extract(request);
        if (token == null) {
            throw new AuthorizationException("토큰이 없습니다😤");
        }
        if (authService.isInvalidToken(token)) {
            throw new AuthorizationException();
        }
    }
}
