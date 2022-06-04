package woowacourse.auth.ui;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.exception.AuthorizationFailureException;
import woowacourse.auth.support.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public LoginInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        if (isExclude(request.getMethod())) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        if (token == null || !authService.isValidToken(token)) {
            throw new AuthorizationFailureException();
        }

        return true;
    }

    private boolean isExclude(final String method) {
        return HttpMethod.POST.name().equals(method) || HttpMethod.OPTIONS.name().equals(method);
    }
}
