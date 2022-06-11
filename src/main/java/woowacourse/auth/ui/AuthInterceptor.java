package woowacourse.auth.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.AuthException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreflight(request)) {
            return true;
        }
        String accessToken = AuthorizationExtractor.extract(Objects.requireNonNull(request))
                .orElseThrow(() -> new AuthException("토큰 값이 잘못되었습니다."));
        String payload = jwtTokenProvider.getPayload(accessToken);
        request.setAttribute("principal", payload);
        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}
