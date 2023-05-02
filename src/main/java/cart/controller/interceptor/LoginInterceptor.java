package cart.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC_TYPE = "Basic";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String authorization = request.getHeader(AUTHORIZATION);
        if (authorization == null || !authorization.startsWith(BASIC_TYPE)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보를 다시 확인해주세요.");
        }
        request.setAttribute(BASIC_TYPE, authorization.substring(BASIC_TYPE.length()).trim());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
