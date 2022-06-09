package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import woowacourse.shoppingcart.exception.custum.AuthorizationException;

public class TokenInterceptor implements AsyncHandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // OPTIONS 요청이라면 항상 허용하도록 설정
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String accessToken = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthorizationException();
        }
        return true;
    }
}
