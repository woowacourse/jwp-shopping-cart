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
        if (ExcludedApi.matches(request)) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        if (token == null || !authService.isValidToken(token)) {
            throw new AuthorizationFailureException();
        }

        return true;
    }

    enum ExcludedApi {
        SIGN_UP(HttpMethod.POST, "/api/customer"),
        PRE_FLIGHT(HttpMethod.OPTIONS, "");

        private final HttpMethod method;
        private final String uri;

        ExcludedApi(final HttpMethod method, final String uri) {
            this.method = method;
            this.uri = uri;
        }

        static boolean matches(final HttpServletRequest request) {
            return Arrays.stream(values())
                    .anyMatch(api -> api.isExcluded(request))
                    || isPreFlight(request);
        }

        static boolean isPreFlight(final HttpServletRequest request) {
            return PRE_FLIGHT.method.name().equals(request.getMethod());
        }

        private boolean isExcluded(final HttpServletRequest request) {
            return request.getMethod().equals(this.method.name())
                    && request.getRequestURI().equals(this.uri);
        }
    }
}
