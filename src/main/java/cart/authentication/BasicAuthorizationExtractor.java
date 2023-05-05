package cart.authentication;

import cart.controller.dto.AuthInfo;
import cart.exception.auth.NotSignInException;
import cart.exception.auth.NotValidTokenFormatException;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final String NOT_VALID_TOKEN_FORMAT_MESSAGE = "Token 형식이 올바르지 않습니다.";

    public AuthInfo extract(String header) {
        validateAuthorizationHeader(header);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decoded = new String(Base64.decodeBase64(authHeaderValue));

        return convertToAuthInfo(decoded);
    }

    private AuthInfo convertToAuthInfo(String decoded) {
        String[] credentials = decoded.split(DELIMITER);

        try {
            String email = credentials[EMAIL_INDEX];
            String password = credentials[PASSWORD_INDEX];

            return new AuthInfo(email, password);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new NotValidTokenFormatException(NOT_VALID_TOKEN_FORMAT_MESSAGE);
        }
    }

    private void validateAuthorizationHeader(String header) {
        if (header == null) {
            throw new NotSignInException("로그인이 필요한 기능입니다.");
        }
        if (checkNonBasicType(header)) {
            throw new NotValidTokenFormatException(NOT_VALID_TOKEN_FORMAT_MESSAGE);
        }
    }

    private boolean checkNonBasicType(String header) {
        return !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
