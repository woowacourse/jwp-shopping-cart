package cart.auth;

import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

@Component
public final class BasicAuthExtractor {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_OFFSET = 0;
    private static final int PASSWORD_OFFSET = 1;

    public UserInfo extract(String authorizationValue) throws AuthenticationException {
        if (authorizationValue == null || !authorizationValue.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new AuthenticationException("사용자 인증이 필요합니다.");
        }

        String authHeaderValue = authorizationValue.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);

        return new UserInfo(credentials[EMAIL_OFFSET], credentials[PASSWORD_OFFSET]);
    }
}
