package woowacourse.auth.ui;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        String token = AuthorizationExtractor.extract(request);
        if (isValid(token)) {
            return true;
        }
        response.setStatus(UNAUTHORIZED.value());
        return false;
    }

    private boolean isValid(String token) {
        return token != null && jwtTokenProvider.validateToken(token);
    }
}
