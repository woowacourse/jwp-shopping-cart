package cart.web.controller.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginCheckInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String header = request.getHeader("Authorization");
        System.out.println("header = " + header);
        if (header == null) {
            response.sendRedirect("/settings?requestUrl=/");
            return false;
        }
        return true;
    }
}
