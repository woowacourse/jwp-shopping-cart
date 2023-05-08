package cart.infratstructure;

import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor {
    private static final String BASIC_TYPE = "basic";
    private static final String DELIMITER = ":";
    private static final int CREDENTIALS_EMAIL_INDEX = 0;
    private static final int CREDENTIALS_PASSWORD_INDEX = 1;

    @Override
    public AuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && (header.toLowerCase().startsWith(BASIC_TYPE))) {
            return extractFrom(header);
        }
        return null;
    }

    private AuthInfo extractFrom(String header) {
        String[] credentials = decodeAuthHeaderValue(header).split(DELIMITER);
        String email = credentials[CREDENTIALS_EMAIL_INDEX];
        String password = credentials[CREDENTIALS_PASSWORD_INDEX];
        return new AuthInfo(email, password);
    }

    private String decodeAuthHeaderValue(final String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        return new String(decodedBytes);
    }
}
