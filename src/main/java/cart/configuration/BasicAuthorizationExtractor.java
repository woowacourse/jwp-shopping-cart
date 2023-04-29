package cart.configuration;

import cart.controller.dto.AuthInfo;
import cart.exception.auth.NotSignInException;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public AuthInfo extract(String header) {
        validateAuthorizationHeader(header);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decoded = new String(Base64.decodeBase64(authHeaderValue));

        String[] credentials = decoded.split(DELIMITER);
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];

        return new AuthInfo(email, password);
    }

    private void validateAuthorizationHeader(final String header) {
        if (header == null || checkNonBasicType(header)) {
            throw new NotSignInException("로그인이 필요한 기능입니다.");
        }
    }

    private boolean checkNonBasicType(String header) {
        return !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
