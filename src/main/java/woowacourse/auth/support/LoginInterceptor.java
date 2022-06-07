package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.exception.InvalidTokenException;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider, AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("POST") && request.getRequestURI().equals("/api/customers")) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);

        if (jwtTokenProvider.validateToken(token)) {
            authService.validateExistCustomerByToken(token);
            return true;
        }

        throw new InvalidTokenException();
    }
}
