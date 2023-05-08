package cart.config.auth;

import cart.exception.customexceptions.UnauthorizedException;
import cart.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Base64AuthInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "authorization";
    public static final String BASIC = "Basic";

    private final AuthService authService;

    public Base64AuthInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authValue = request.getHeader(AUTHORIZATION_HEADER);
        if (authValue == null) {
            throw new UnauthorizedException("로그인을 해주세요.");
        }

        if (!authValue.startsWith(BASIC)) {
            throw new UnauthorizedException("지원되지 않는 인코딩 타입입니다.");
        }

        final String authValueWithBase64Encoding = authValue.substring(BASIC.length()).trim();
        final String auth = new String(Base64Utils.decodeFromString(authValueWithBase64Encoding));

        final String[] emailAndPasswordWithDecryption = auth.split(":");
        final String email = emailAndPasswordWithDecryption[0];
        final String password = emailAndPasswordWithDecryption[1];
        authService.validateMember(email, password);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
