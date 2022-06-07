package woowacourse.config;

import static java.util.Arrays.stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.utils.AuthorizationExtractor;
import woowacourse.utils.JwtTokenProvider;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (AllowApiList.matches(request)) {
            return true;
        }
        String token = AuthorizationExtractor.extract(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        return true;
    }

    @RequiredArgsConstructor
    private enum AllowApiList {

        CUSTOMERS_POST(HttpMethod.POST, "/api/customers");

        private final HttpMethod method;
        private final String uri;

        public static boolean matches(HttpServletRequest request) {
            return stream(values())
                    .anyMatch(api -> matchesInternal(api, request));
        }

        private static boolean matchesInternal(AllowApiList api, HttpServletRequest request) {
            return api.method.matches(request.getMethod()) && api.uri.equals(request.getRequestURI());
        }
    }
}
