package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public LoginInterceptor(final JwtTokenProvider jwtTokenProvider,
                            final AuthenticationContext authenticationContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (isPreflight(request)) {
            return true;
        }
        String accessToken = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(accessToken)) {
            String payload = jwtTokenProvider.getPayload(accessToken);
            authenticationContext.setPrincipal(payload);
            return true;
        }
        throw new InvalidAuthException("인증되지 않은 토큰입니다.");
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}
