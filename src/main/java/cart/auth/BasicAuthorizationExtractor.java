package cart.auth;

import cart.controller.dto.AuthInfo;
import cart.exception.AuthException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final String HEADER_EMPTY_ERROR_MESSAGE = "인증 정보가 없습니다.";
    private static final String HEADER_INVALID_ERROR_MESSAGE = "올바르지 않은 헤더입니다.";
    private static final int EMAIL_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;

    public AuthInfo extract(String authHeader) {

        if (authHeader == null) {
            throw new AuthException(HEADER_EMPTY_ERROR_MESSAGE);
        }

        if (isInvalidHeader(authHeader)) {
            throw new AuthException(HEADER_INVALID_ERROR_MESSAGE);
        }

        String authHeaderValue = authHeader.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];

        return new AuthInfo(email, password);
    }

    private static boolean isInvalidHeader(String header) {
        return !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
