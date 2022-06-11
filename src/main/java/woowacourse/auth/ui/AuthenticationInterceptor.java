package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.InvalidTokenException;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        validateToken(token);

        String payload = jwtTokenProvider.getPayload(token);
        request.setAttribute("payload", payload);
        return true;
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException("토큰 정보가 없습니다.");
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException("유효하지 않거나 만료된 토큰입니다.");
        }
    }

}
