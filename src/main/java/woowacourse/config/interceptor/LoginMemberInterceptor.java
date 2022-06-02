package woowacourse.config.interceptor;

import java.util.Objects;
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
        if (isSigningUp(request)) {
            return true;
        }

        final String token = bearerExtractor.extract(request);
        if (Objects.isNull(token) || token.isBlank()) {
            logUnauthorizedRequest(request, token);
            return false;
        }

        setPayloadToRequest(request, token);
        return true;
    }

    private boolean isSigningUp(HttpServletRequest request) {
        return HttpMethod.POST.matches(request.getMethod());
    }

    private void logUnauthorizedRequest(HttpServletRequest request, String token) {
        logger.error("unauthorized request to : {} : {}, with token {}",
                request.getRequestURI(), request.getMethod(), token);
    }

    private void setPayloadToRequest(HttpServletRequest request, String token) {
        request.setAttribute("payload", tokenProvider.getPayload(token));
    }
}
