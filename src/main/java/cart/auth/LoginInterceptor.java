package cart.auth;

import cart.controller.exception.IncorrectAuthorizationMethodException;
import cart.controller.exception.MissingAuthorizationHeaderException;
import cart.controller.exception.UncertifiedMemberException;
import cart.dto.auth.AuthInfo;
import cart.service.MembersService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int CREDENTIALS_EMAIL_INDEX = 0;
    public static final int CREDENTIALS_PASSWORD_INDEX = 1;

    private final MembersService membersService;

    public LoginInterceptor(MembersService membersService) {
        this.membersService = membersService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw new MissingAuthorizationHeaderException();
        }

        if (accessToken.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            return handleAuthorization(request, accessToken);
        }

        throw new IncorrectAuthorizationMethodException();
    }

    private boolean handleAuthorization(HttpServletRequest request, String accessToken) {
        String[] credentials = getCredentialsByBase64Decoding(accessToken);

        String email = credentials[CREDENTIALS_EMAIL_INDEX];
        String password = credentials[CREDENTIALS_PASSWORD_INDEX];

        validateAuthorization(new AuthInfo(email, password));
        setCredentialsInRequest(request, email, password);

        return true;
    }

    private String[] getCredentialsByBase64Decoding(String accessToken) {
        String authHeader = accessToken.substring(BASIC_TYPE.length()).strip();
        String decodedAuthHeader = new String(Base64.decodeBase64(authHeader));
        String[] credentials = decodedAuthHeader.split(DELIMITER);

        return credentials;
    }

    private void validateAuthorization(AuthInfo authInfo) {
        if (!membersService.isMemberCertified(authInfo)) {
            throw new UncertifiedMemberException();
        }
    }

    private void setCredentialsInRequest(HttpServletRequest request, String email, String password) {
        request.setAttribute("email", email);
        request.setAttribute("password", password);
    }
}
