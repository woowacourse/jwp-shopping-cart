package cart.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (request.getHeader(AUTHORIZATION) == null) {
            throw new AuthenticationException("사용자 인증이 필요합니다.");
        }

        return true;
    }
}
