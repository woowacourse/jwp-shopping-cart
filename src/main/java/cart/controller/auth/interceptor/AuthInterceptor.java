package cart.controller.auth.interceptor;

import cart.controller.auth.dto.LoginUser;
import cart.controller.auth.util.BasicAuthorizationExtractor;
import cart.exception.UnauthorizedException;
import cart.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    public AuthInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        LoginUser loginUser = BasicAuthorizationExtractor.extract(request.getHeader("authorization"));
        if (userService.checkLogin(loginUser.getEmail(), loginUser.getPassword())) {
            return super.preHandle(request, response, handler);
        }
        throw new UnauthorizedException("로그인 정보가 잘못되었습니다.");
    }
}
