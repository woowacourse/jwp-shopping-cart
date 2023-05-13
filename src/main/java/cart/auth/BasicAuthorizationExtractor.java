package cart.auth;

import cart.controller.dto.MemberDto;
import cart.exception.AuthException;
import java.util.regex.Pattern;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor {

    private static final String BASIC_REGEX = "^Basic [A-Za-z0-9+/]+=*$";
    private static final String AUTHORIZATION_DELIMITER = " ";
    private static final String AUTHORIZATION_VALUE_DELIMITER = ":";
    private static final String AUTH_EMPTY_ERROR_MESSAGE = "인증 정보가 없습니다.";
    private static final String HEADER_INVALID_ERROR_MESSAGE = "올바르지 않은 헤더입니다.";
    private static final Pattern BASIC_AUTH_PATTERN = Pattern.compile(BASIC_REGEX);
    private static final int AUTHORIZATION_VALUE_INDEX = 1;
    private static final int NAME_INDEX = 0;
    private static final int EMAIL_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;
    private static final int CREDENTIAL_SIZE = 3;

    public MemberDto extract(String authorization) {
        validate(authorization);

        String[] credentials = getCredentials(authorization);
        validateCredentials(credentials);

        String name = credentials[NAME_INDEX];
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];
        return new MemberDto(email, password, name);
    }

    private void validate(String authorization) {
        if (authorization == null) {
            throw new AuthException(AUTH_EMPTY_ERROR_MESSAGE);
        }

        if (isInvalidHeader(authorization)) {
            throw new AuthException(HEADER_INVALID_ERROR_MESSAGE);
        }
    }

    private boolean isInvalidHeader(String authorization) {
        return !BASIC_AUTH_PATTERN.matcher(authorization).matches();
    }

    private String[] getCredentials(String authorization) {
        String encodedValue = authorization.split(AUTHORIZATION_DELIMITER)[AUTHORIZATION_VALUE_INDEX];
        if (encodedValue.isBlank()) {
            throw new AuthException(HEADER_INVALID_ERROR_MESSAGE);
        }

        byte[] decodedBytes = Base64.decodeBase64(encodedValue);
        String decodedValue = new String(decodedBytes);
        return decodedValue.split(AUTHORIZATION_VALUE_DELIMITER);
    }

    private void validateCredentials(String[] credentials) {
        if (credentials.length != CREDENTIAL_SIZE) {
            throw new AuthException(HEADER_INVALID_ERROR_MESSAGE);
        }
    }
}
