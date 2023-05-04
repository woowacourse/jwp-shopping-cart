package cart.controller.config.interceptor;

import cart.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    // TODO: 5/3/23 엥...? 뭔가 이상하다...
    public AuthInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        if (authorization.toLowerCase().startsWith("basic")) {
            String credentials = authorization.split("\\s")[1];
            byte[] bytes = Base64.decodeBase64(credentials);
            String[] emailAndPassword = new String(bytes).split(":");
            String email = emailAndPassword[0];
            String password = emailAndPassword[1];
            if (userService.checkLogin(email, password)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        return super.preHandle(request, response, handler);
    }
}
