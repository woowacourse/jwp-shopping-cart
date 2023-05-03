package cart.config.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String auth = request.getHeader(HEADER_AUTHORIZATION);

        if (Objects.isNull(auth)) {
            throw new BasicAuthException("토큰이 존재하지 않습니다.");
        }

        return true;
    }
}
