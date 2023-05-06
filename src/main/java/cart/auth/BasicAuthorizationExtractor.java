package cart.auth;

import cart.controller.exception.IncorrectAuthorizationMethodException;
import cart.controller.exception.MissingAuthorizationHeaderException;
import cart.dto.auth.AuthInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int CREDENTIALS_EMAIL_INDEX = 0;
    public static final int CREDENTIALS_PASSWORD_INDEX = 1;

    public AuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            throw new MissingAuthorizationHeaderException();
        }

        if (header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            String authHeader = header.substring(BASIC_TYPE.length()).strip();
            String decodedAuthHeader = new String(Base64.decodeBase64(authHeader));

            String[] credentials = decodedAuthHeader.split(DELIMITER);
            String email = credentials[CREDENTIALS_EMAIL_INDEX];
            String password = credentials[CREDENTIALS_PASSWORD_INDEX];

            return new AuthInfo(email, password);
        }

        throw new IncorrectAuthorizationMethodException();
    }
}
