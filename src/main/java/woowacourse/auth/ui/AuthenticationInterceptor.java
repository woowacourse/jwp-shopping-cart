package woowacourse.auth.ui;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.TokenExpiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String TOKEN_PAYLOAD = "TOKEN_PAYLOAD";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        final String accessToken = AuthorizationExtractor.extract(request);
        validateTokenExpired(accessToken);

        request.setAttribute(TOKEN_PAYLOAD, jwtTokenProvider.getPayload(accessToken));
        return true;
    }

    private void validateTokenExpired(final String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new TokenExpiredException();
        }
    }
}
