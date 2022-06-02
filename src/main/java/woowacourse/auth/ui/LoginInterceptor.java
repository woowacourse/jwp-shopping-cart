package woowacourse.auth.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class LoginInterceptor implements HandlerInterceptor {

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
        validateExistAuthHeader(request);
        String token = AuthorizationExtractor.extract(request);
        validateToken(token);
        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateExistAuthHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (Objects.isNull(authorizationHeader)) {
            throw new AuthorizationException("로그인이 필요합니다.");
        }
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }
}
