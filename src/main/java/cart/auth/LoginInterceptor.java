package cart.auth;

import cart.exception.AuthorizationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new AuthorizationException("사용자 인증이 필요합니다.");
        }

        return true;
    }
}
