package cart.auth;

import javax.servlet.http.HttpServletRequest;

public class UserExtractor {

    private final AuthorizationExtractor authorizationExtractor;
    private final AuthService authService;

    public UserExtractor(AuthorizationExtractor authorizationExtractor, AuthService authService) {
        this.authorizationExtractor = authorizationExtractor;
        this.authService = authService;
    }

    public User extract(HttpServletRequest request) {
        AuthInfo authInfo = authorizationExtractor.extract(request);
        String email = authInfo.getEmail();

        return authService.findMemberByEmail(email);
    }
}
