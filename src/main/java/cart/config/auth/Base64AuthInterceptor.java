package cart.config.auth;

import cart.exception.customExceptions.AdminAccessException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Base64AuthInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "authorization";
    public static final String BASIC = "Basic";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authValue = request.getHeader(AUTHORIZATION_HEADER);
        if (authValue == null) {
            throw new AdminAccessException("로그인을 해주세요.");
        }

        if (!authValue.startsWith(BASIC)) {
            throw new AdminAccessException("지원되지 않는 인코딩 타입입니다.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
