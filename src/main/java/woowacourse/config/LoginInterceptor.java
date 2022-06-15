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

        CUSTOMERS_SAVE(HttpMethod.POST, "/api/customers"),
        PRODUCT_DETAIL(HttpMethod.GET, "/api/products"),
        PRODUCT_LIST(HttpMethod.GET, "/api/products/*");

        public static final String ANY_STRING = "*";
        private final HttpMethod method;
        private final String uri;

        public static boolean matches(HttpServletRequest request) {
            return stream(values())
                    .anyMatch(api -> matchesInternal(api, request));
        }

        private static boolean matchesInternal(AllowApiList api, HttpServletRequest request) {
            return matchesMethod(api, request) && matchesUri(api, request);
        }

        private static boolean matchesMethod(AllowApiList api, HttpServletRequest request) {
            return api.method.matches(request.getMethod());
        }

        private static boolean matchesUri(AllowApiList api, HttpServletRequest request) {
            if (api.uri.endsWith(ANY_STRING)) {
                String uri = api.uri.replace(ANY_STRING, "");
                return request.getRequestURI().startsWith(uri);
            }
            return request.getRequestURI().equals(api.uri);
        }
    }
}
