package woowacourse.shoppingcart.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.UnauthorizedTokenException;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        boolean isValidate = jwtTokenProvider.validateToken(token);
        if (!isValidate) {
            throw new UnauthorizedTokenException();
        }
        return true;
    }
}
