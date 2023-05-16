package cart.auth.interceptor;

import cart.auth.BasicAuthorizationExtractor;
import cart.controller.dto.auth.AuthInfoDto;
import cart.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor;
    private final UserService userService;

    public LoginInterceptor(BasicAuthorizationExtractor basicAuthorizationExtractor, UserService userService) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthInfoDto authInfoDto = basicAuthorizationExtractor.extract(request);
        userService.isExistUser(authInfoDto);

        return super.preHandle(request, response, handler);
    }
}
