package cart.controller;

import cart.exception.AuthorizationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            throw new AuthorizationException("헤더에 토큰이 들어있지 않습니다.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
