package woowacourse.shoppingcart.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.exception.UnauthorizedTokenException;
import woowacourse.shoppingcart.support.AuthorizationExtractor;
import woowacourse.shoppingcart.support.JwtTokenProvider;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (isPreflight(request)) {
            return true;
        }

        validateToken(request);
        return true;
    }

    private boolean isPreflight(final HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateToken(final HttpServletRequest request) {
        final String token = AuthorizationExtractor.extract(request);
        final boolean isValidToken = jwtTokenProvider.validateToken(token);
        if (isValidToken) {
            return;
        }
        throw new UnauthorizedTokenException();
    }
}
