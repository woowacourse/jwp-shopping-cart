package cart.auth;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenAuthResolver extends HandlerMethodArgumentResolver {
    void validateToken(HttpServletRequest servletRequest);

    List<String> extractToken(HttpServletRequest servletRequest);
}
