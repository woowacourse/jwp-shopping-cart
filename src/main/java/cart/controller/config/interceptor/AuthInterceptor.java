package cart.controller.config.interceptor;

import cart.exception.UnauthorizedException;
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

    public AuthInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        if (authorization == null ||authorization.isBlank()) {
            throw new UnauthorizedException("인증 정보가 없습니다.");
        }
        if (authorization.toLowerCase().startsWith("basic")) {
            String credentials = authorization.split("\\s")[1];
            byte[] bytes = Base64.decodeBase64(credentials);
            String[] emailAndPassword = new String(bytes).split(":");
            String email = emailAndPassword[0];
            String password = emailAndPassword[1];
            if (userService.checkLogin(email, password)) {
                return super.preHandle(request, response, handler);
            }
            throw new UnauthorizedException("로그인 정보가 잘못되었습니다.");
        }
        throw new UnauthorizedException("인증 정보가 잘못되었습니다.");
    }
}
