package cart.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION = "Authorization";
    private final String authType;

    public LoginInterceptor(String authType) {
        this.authType = authType;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String authorization = request.getHeader(AUTHORIZATION);
        if (authorization == null || !authorization.startsWith(authType)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보를 다시 확인해주세요.");
        }
        request.setAttribute(authType, authorization.substring(authType.length()).trim());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
