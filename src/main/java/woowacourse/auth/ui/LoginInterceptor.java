package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.AuthorizationException;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (isPreflight(request)) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        validateToken(token);
        request.setAttribute(ACCESS_TOKEN, token);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new AuthorizationException("토큰이 존재하지 않습니다.");
        }
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("인증되지 않은 회원입니다.");
        }
    }
}
