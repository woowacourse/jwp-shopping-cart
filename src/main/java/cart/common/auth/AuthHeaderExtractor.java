package cart.common.auth;

import cart.exception.AuthException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthHeaderExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public boolean authenticate(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new AuthException("인증 정보가 존재하지 않습니다.");
        }
        if (!authorizationHeader.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new AuthException("올바르지 않은 인증 방식입니다.");
        }
        return true;
    }

    public List<String> extract(String authorizationHeader) {
        String authValue = authorizationHeader.substring(BASIC_TYPE.length()).trim();
        byte[] decodedAuthByteValue = Base64.decodeBase64(authValue);
        String decodedAuthString = new String(decodedAuthByteValue);

        List<String> headerValues = Arrays.asList(decodedAuthString.split(DELIMITER));
        if (headerValues.size() != 2) {
            throw new AuthException("인증 정보 형식이 올바르지 않습니다.");
        }
        return headerValues;
    }
}
