package cart.auth;

import cart.controller.exception.MissingAuthorizationHeaderException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw new MissingAuthorizationHeaderException();
        }

        return true;
    }
}
