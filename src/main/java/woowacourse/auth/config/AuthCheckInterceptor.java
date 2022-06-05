package woowacourse.auth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.InvalidTokenException;

public class AuthCheckInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthCheckInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new InvalidTokenException();
    }
}
