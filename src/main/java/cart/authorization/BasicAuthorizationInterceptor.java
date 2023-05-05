package cart.authorization;

import cart.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BasicAuthorizationInterceptor extends HandlerInterceptorAdapter {
    private final UserService userService;

    public BasicAuthorizationInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String header = request.getHeader("Authorization");
        return userService.authUser(BasicAuthorizationExtractor.extract(header));
    }
}
