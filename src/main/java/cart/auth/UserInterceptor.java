package cart.auth;

import cart.exception.AuthorizationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cart.auth.AuthConstant.AUTHORIZATION;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new AuthorizationException(String.format("%s 헤더에 값이 들어있지 않습니다.", AUTHORIZATION));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
