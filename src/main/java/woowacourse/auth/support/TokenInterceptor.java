package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.exception.AuthorizationException;

public class TokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        String accessToken = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(accessToken) || accessToken == null) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        return true;
    }
}
