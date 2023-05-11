package cart.web.auth;

import cart.exception.AuthorizationException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final int INDEX_OF_EMAIL = 0;
    private static final int INDEX_OF_PASSWORD = 1;
    private static final int SIZE_OF_CREDENTIAL = 2;
    private static final String DELIMITER = ":";

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BasicAuthorizationExtractor extractor = BasicAuthorizationExtractor.getInstance();

        String decodedAuthorization = extractor.extract(request);
        UserInfo userInfo = createUserInfo(decodedAuthorization);

        authService.authenticate(userInfo);

        return super.preHandle(request, response, handler);
    }

    private UserInfo createUserInfo(String authInfo) {
        String[] credentials = authInfo.split(DELIMITER);

        if (credentials.length != SIZE_OF_CREDENTIAL) {
            throw new AuthorizationException();
        }

        String email = credentials[INDEX_OF_EMAIL];
        String password = credentials[INDEX_OF_PASSWORD];

        return new UserInfo(email, password);
    }
}
