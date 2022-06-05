package woowacourse.auth.ui;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.exception.AuthorizationFailureException;
import woowacourse.auth.support.AuthorizationExtractor;

public class LoginInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public LoginInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        if (ExcludeApi.matches(request)) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        if (token == null || !authService.isValidToken(token)) {
            throw new AuthorizationFailureException();
        }

        return true;
    }

    enum ExcludeApi {
        SIGN_UP(HttpMethod.POST, "/api/customer");

        private final HttpMethod method;
        private final String uri;

        ExcludeApi(final HttpMethod method, final String uri) {
            this.method = method;
            this.uri = uri;
        }

        static boolean matches(final HttpServletRequest request) {
            return Arrays.stream(values())
                    .anyMatch(api -> api.isExclude(request));
        }

        private boolean isExclude(final HttpServletRequest request) {
            return request.getMethod().equals(this.method.name())
                    && request.getRequestURI().equals(this.uri);
        }
    }
}
