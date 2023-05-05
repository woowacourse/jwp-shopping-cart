package cart.auth;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

public interface TokenAuthResolver extends HandlerMethodArgumentResolver {
    void validateToken(HttpServletRequest servletRequest);
}
