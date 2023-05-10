package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public AuthInfo resolveAuthInfo(final String authHeader) {
        if (authHeader == null) {
            throw new UnauthorizedException();
        }

        if ((authHeader.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authValue = authHeader.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authValue);
            String decodedString = new String(decodedBytes);
            String[] credentials = decodedString.split(DELIMITER);
            return new AuthInfo(credentials[EMAIL_INDEX], credentials[PASSWORD_INDEX]);
        }
        throw new UnauthorizedException();
    }
}
