package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class LoginInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        authService.validateToken(token);
        authService.validateExistUser(jwtTokenProvider.getPayload(token));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
