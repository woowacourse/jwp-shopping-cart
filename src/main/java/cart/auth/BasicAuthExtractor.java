package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public final class BasicAuthExtractor {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public UserInfo extract(String header) throws AuthenticationException {
        if (header == null || !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new AuthenticationException("사용자 인증이 필요합니다.");
        }

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);

        return new UserInfo(credentials[0], credentials[1]);
    }
}
