package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.AuthorizationException;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = AuthorizationExtractor.extract(request);
        authService.validateToken(token);
        authService.validateExistUser(jwtTokenProvider.getPayload(token));

        return super.preHandle(request, response, handler);
    }
}
