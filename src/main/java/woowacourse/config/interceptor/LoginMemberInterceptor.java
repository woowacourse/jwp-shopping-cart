package woowacourse.config.interceptor;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.domain.BearerExtractor;
import woowacourse.auth.domain.TokenProvider;

public class LoginMemberInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LoginMemberInterceptor.class);
    private final TokenProvider tokenProvider;
    private final BearerExtractor bearerExtractor;

    public LoginMemberInterceptor(TokenProvider tokenProvider, BearerExtractor bearerExtractor) {
        this.tokenProvider = tokenProvider;
        this.bearerExtractor = bearerExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (ExcludeRule.isExcluded(request)) {
            return true;
        }

        final String token = bearerExtractor.extract(request);
        if (Objects.isNull(token) || token.isBlank()) {
            logUnauthorizedRequest(request, token);
            response.setStatus(401);
            return false;
        }

        setPayloadToRequest(request, token);
        return true;
    }

    private void logUnauthorizedRequest(HttpServletRequest request, String token) {
        logger.error("unauthorized request to : {} : {}, with token {}",
                request.getRequestURI(), request.getMethod(), token);
    }

    private void setPayloadToRequest(HttpServletRequest request, String token) {
        request.setAttribute("payload", tokenProvider.getPayload(token));
    }

    enum ExcludeRule {
        OPTIONS(HttpMethod.OPTIONS, "/api/customer/**"),
        SIGN_UP(HttpMethod.POST, "/api/customer"),
        ;

        private final HttpMethod httpMethod;
        private final String requestURI;

        ExcludeRule(HttpMethod httpMethod, String requestURI) {
            this.httpMethod = httpMethod;
            this.requestURI = requestURI;
        }

        public static boolean isExcluded(HttpServletRequest request) {
            return Arrays.stream(values())
                    .filter(isExcludedMethod(request))
                    .anyMatch(isExcludedURI(request));
        }

        private static Predicate<ExcludeRule> isExcludedMethod(HttpServletRequest request) {
            return rule -> rule.httpMethod.name().equals(request.getMethod());
        }

        private static Predicate<ExcludeRule> isExcludedURI(HttpServletRequest request) {
            return rule -> Objects.equals(rule.requestURI, request.getRequestURI());
        }
    }
}
