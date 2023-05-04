package cart.auth;

import cart.exception.BasicAuthException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) throws Exception {
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new BasicAuthException("사용자 인증 정보가 존재하지 않습니다.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
