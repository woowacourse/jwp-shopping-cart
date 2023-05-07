package cart.controller.auth.interceptor;

import cart.controller.auth.Authorization;
import cart.controller.auth.dto.LoginUser;
import cart.controller.auth.util.BasicAuthorizationExtractor;
import cart.exception.UnauthorizedException;
import cart.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final Authorization authorization;

    public AuthInterceptor(final Authorization authorization) {
        this.authorization = authorization;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        LoginUser loginUser = BasicAuthorizationExtractor.extract(request.getHeader("authorization"));
        if (authorization.checkLogin(loginUser.getEmail(), loginUser.getPassword())) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        throw new UnauthorizedException("로그인 정보가 잘못되었습니다.");
    }
}
