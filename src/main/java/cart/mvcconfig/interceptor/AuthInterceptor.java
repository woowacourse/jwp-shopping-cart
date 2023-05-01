package cart.mvcconfig.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cart.exception.AuthorizationException;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER_KEY = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authValue = request.getHeader(AUTH_HEADER_KEY);
        if (authValue == null) {
            throw new AuthorizationException("AUTH 헤더가 존재하지 않습니다.");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
