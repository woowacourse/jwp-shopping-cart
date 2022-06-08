package woowacourse.member.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.member.exception.AuthorizationException;
import woowacourse.member.support.AuthorizationExtractor;
import woowacourse.member.support.TokenManager;

public class LoginInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    public LoginInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreflight(request)) {
            return true;
        }
        validateExistHeader(request);
        String token = AuthorizationExtractor.extract(request);
        validateToken(token);
        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateExistHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (Objects.isNull(authorizationHeader)) {
            throw new AuthorizationException("로그인이 필요합니다.");
        }
    }

    private void validateToken(String token) {
        if (!tokenManager.validateToken(token)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }
}
