package woowacourse.auth.interceptor;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.exception.unauthorization.UnauthorizedException;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String POST_METHOD = HttpMethod.POST.name();
    private static final String SIGN_UP_URI = "/api/customer";
    public static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        if (isSignUpWithNoToken(request)) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        if (Objects.isNull(token) || token.isBlank()) {
            throw new UnauthorizedException();
        }
        request.setAttribute(TOKEN, token);
        return true;
    }

    private boolean isSignUpWithNoToken(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        final String requestMethod = request.getMethod();
        return (requestURI.equals(SIGN_UP_URI)) && (requestMethod.equals(POST_METHOD));
    }

}
