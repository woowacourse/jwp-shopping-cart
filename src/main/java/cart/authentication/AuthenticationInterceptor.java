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

    public AuthenticationInterceptor(BasicAuthorizationExtractor extractor, AuthenticationValidator authValidator) {
        this.extractor = extractor;
        this.authValidator = authValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String header = request.getHeader(AUTHORIZATION);
        AuthInfo authInfo = extractor.extract(header);

        authValidator.validate(authInfo);
        request.setAttribute(AUTH_INFO_KEY, authInfo);

        return true;
    }
}
