package cart.web.auth;

import cart.exception.AuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor {
    private static final BasicAuthorizationExtractor EXTRACTOR = new BasicAuthorizationExtractor();
    private static final int INDEX_OF_EMAIL = 0;
    private static final int INDEX_OF_PASSWORD = 1;
    private static final int SIZE_OF_CREDENTIAL = 2;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "basic";
    private static final String DELIMITER = ":";

    private BasicAuthorizationExtractor() {
    }

    public static BasicAuthorizationExtractor getInstance() {
        return EXTRACTOR;
    }

    public UserInfo extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new AuthorizationException();
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);

            if (credentials.length != SIZE_OF_CREDENTIAL) {
                throw new AuthorizationException();
            }

            String email = credentials[INDEX_OF_EMAIL];
            String password = credentials[INDEX_OF_PASSWORD];

            return new UserInfo(email, password);
        }

        throw new AuthorizationException();
    }
}
