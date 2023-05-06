package cart.authentication;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.controller.dto.AuthInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    static final String AUTH_INFO_KEY = "authInfo";

    private final BasicAuthorizationExtractor extractor;
    private final AuthenticationValidator authValidator;
    private final AuthInfoThreadLocal authInfoThreadLocal;

    public AuthenticationInterceptor(BasicAuthorizationExtractor extractor, AuthenticationValidator authValidator,
            AuthInfoThreadLocal authInfoThreadLocal) {
        this.extractor = extractor;
        this.authValidator = authValidator;
        this.authInfoThreadLocal = authInfoThreadLocal;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String header = request.getHeader(AUTHORIZATION);
        AuthInfo authInfo = extractor.extract(header);

        authValidator.validate(authInfo);
        authInfoThreadLocal.set(authInfo);

        return true;
    }
}
