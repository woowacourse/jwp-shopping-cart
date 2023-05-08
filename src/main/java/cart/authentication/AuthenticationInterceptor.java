package cart.authentication;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.controller.dto.AuthInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private final BasicAuthorizationExtractor extractor;
    private final AuthenticationValidator authValidator;
    private final AuthInfoStore authInfoStore;

    public AuthenticationInterceptor(BasicAuthorizationExtractor extractor, AuthenticationValidator authValidator,
            AuthInfoStore authInfoStore) {
        this.extractor = extractor;
        this.authValidator = authValidator;
        this.authInfoStore = authInfoStore;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String header = request.getHeader(AUTHORIZATION);
        AuthInfo authInfo = extractor.extract(header);

        authValidator.validate(authInfo);
        authInfoStore.set(authInfo);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        authInfoStore.remove();
    }
}
