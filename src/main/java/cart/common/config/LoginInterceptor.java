package cart.common.config;

import cart.common.exceptions.AuthorizationException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        Optional.ofNullable(request.getHeader("Authorization"))
                .orElseThrow(() -> new AuthorizationException("유저 인증 정보가 없습니다."));
        return true;
    }
}