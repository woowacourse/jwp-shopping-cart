package cart.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public abstract class LoginInterceptor extends HandlerInterceptorAdapter {

    abstract void authorize(final HttpServletRequest request);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        authorize(request);
        return super.preHandle(request, response, handler);
    }
}
