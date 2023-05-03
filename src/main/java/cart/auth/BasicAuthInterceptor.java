package cart.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authorization = request.getHeader(AUTHORIZATION);

        if (authorization == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
