package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class AuthenticationPrincipalInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String accessToken = AuthorizationExtractor.extract(request);
        jwtTokenProvider.validateToken(accessToken);
        return true;
    }
}
