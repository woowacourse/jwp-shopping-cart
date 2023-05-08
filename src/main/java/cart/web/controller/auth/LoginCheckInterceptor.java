package cart.web.controller.auth;

import cart.exception.UnAuthorizedException;
import cart.web.controller.cart.dto.AuthCredentials;
import cart.web.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public LoginCheckInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final AuthCredentials authCredentials = BasicAuthorizationExtractor.extract(request);
        request.setAttribute("authCredentials", authCredentials);
        if (userService.isExistUser(authCredentials)) {
            return true;
        }

        throw new UnAuthorizedException();
    }
}
