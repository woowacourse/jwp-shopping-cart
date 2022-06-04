package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import woowacourse.shoppingcart.exception.AuthorizationException;

public class TokenInterceptor implements AsyncHandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthorizationException("토큰이 유효하지 않습니다.");
        }
        return true;
    }
}
