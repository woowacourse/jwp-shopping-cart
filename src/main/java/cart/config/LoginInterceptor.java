package cart.config;

import cart.exception.AuthorizationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw new AuthorizationException();
        }

        return super.preHandle(request, response, handler);
    }
}
