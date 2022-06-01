package woowacourse.auth.ui;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.common.exception.UnauthorizedException;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
        String token = AuthorizationExtractor.extract(header);
        jwtTokenProvider.validateToken(token);
        return true;
    }
}
