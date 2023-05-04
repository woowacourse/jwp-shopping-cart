package cart.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor<AuthDto> authorizationExtractor;
    private final AuthService AuthService;

    @Autowired
    public AuthInterceptor(final BasicAuthorizationExtractor authorizationExtractor, final AuthService AuthService) {
        this.authorizationExtractor = authorizationExtractor;
        this.AuthService = AuthService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final AuthDto authDto = authorizationExtractor.extract(request);

        if (AuthService.isInvalidAuth(authDto)) {
            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
        }

        return true;
    }
}
