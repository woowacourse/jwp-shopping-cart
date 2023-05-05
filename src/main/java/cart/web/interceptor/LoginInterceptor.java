package cart.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import cart.domain.member.AuthService;
import cart.web.AuthorizationException;
import cart.web.cart.dto.AuthInfo;

public class LoginInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final AuthService authService;

    public LoginInterceptor(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final AuthService authService) {
        this.authorizationExtractor = authorizationExtractor;
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
        throws Exception {
        final AuthInfo authInfo = authorizationExtractor.extract(request);
        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        if (authService.isNotRegistered(email, password)) {
            throw new AuthorizationException("올바른 인증 정보가 필요합니다.");
        }
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        return true;
    }
}
