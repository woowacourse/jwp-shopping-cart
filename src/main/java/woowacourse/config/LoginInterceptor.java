package woowacourse.config;

import static java.util.Arrays.stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.utils.AuthorizationExtractor;
import woowacourse.utils.JwtTokenProvider;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (AllowList.matches(request)) {
            return true;
        }
        String token = AuthorizationExtractor.extract(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        return true;
    }

    private enum AllowList {

        CUSTOMERS_POST("POST", "/api/customers");

        private final String method;
        private final String uri;

        AllowList(String method, String uri) {
            this.method = method;
            this.uri = uri;
        }

        public static boolean matches(HttpServletRequest request) {
            return stream(values())
                    .anyMatch(api -> matchesInternal(request, api));
        }

        private static boolean matchesInternal(HttpServletRequest request, AllowList api) {
            return request.getMethod().equals(api.method) && request.getRequestURI().equals(api.uri);
        }
    }
}
