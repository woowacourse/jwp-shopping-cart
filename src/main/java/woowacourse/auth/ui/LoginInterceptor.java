package woowacourse.auth.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.TokenManager;

public class LoginInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    public LoginInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        validateExistHeader(request);
        String token = AuthorizationExtractor.extract(request);
        validateToken(token);
        return true;
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
