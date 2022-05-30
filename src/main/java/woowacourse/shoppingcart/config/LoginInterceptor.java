package woowacourse.shoppingcart.config;

import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw new AuthorizationException();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
