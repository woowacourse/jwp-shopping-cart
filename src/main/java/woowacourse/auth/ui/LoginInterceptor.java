package woowacourse.auth.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.global.exception.InvalidTokenException;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        if (request.getMethod().equals("POST") && request.getRequestURI().equals("/api/customers")) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        validateAvailableToken(token);
        validateNotToken(token);
        return true;
    }

    private void validateAvailableToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }
    }

    private void validateNotToken(String token) {
        if (Objects.isNull(token)) {
            throw new InvalidTokenException("토큰이 존재하지 않습니다.");
        }
    }
}
