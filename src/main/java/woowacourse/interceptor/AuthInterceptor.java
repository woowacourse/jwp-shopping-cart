package woowacourse.interceptor;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.exception.auth.UnauthorizedException;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (validateRequestIsSignUp(request)) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        validateTokenIsNull(token);
        request.setAttribute("token", token);
        return true;
    }

    private boolean validateRequestIsSignUp(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        final String requestMethod = request.getMethod();
        final String postMethod = HttpMethod.POST.name();
        return (requestURI.equals("/api/customer")) && (requestMethod.equals(postMethod));
    }

    private void validateTokenIsNull(String token) {
        if (Objects.isNull(token) || token.isBlank()) {
            throw new UnauthorizedException();
        }
    }
}
