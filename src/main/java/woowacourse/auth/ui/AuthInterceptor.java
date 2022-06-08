package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.exception.unauthorized.UnauthorizedException;
import woowacourse.auth.support.JwtTokenExtractor;
import woowacourse.auth.support.JwtTokenProvider;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenExtractor jwtTokenExtractor, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String token = jwtTokenExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException();
        }

        return true;
    }
}
