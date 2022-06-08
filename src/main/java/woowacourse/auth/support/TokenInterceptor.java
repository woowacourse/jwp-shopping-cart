package woowacourse.auth.support;

import static org.springframework.http.HttpMethod.OPTIONS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.exception.AuthorizationException;

public class TokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (OPTIONS.equals(request.getMethod())) {
            return true;
        }
        String accessToken = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(accessToken) || accessToken == null) {
            throw new AuthorizationException("토큰이 유효하지 않습니다.");
        }
        return true;
    }
}
