package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.naming.AuthenticationException;

public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfo extract(String header) throws AuthenticationException {
        if (header != null && isStartsWithBasic(header)) {
            String decodedString = decodeHeader(header);
            return makeAuthInfo(decodedString);
        }

        throw new AuthenticationException("해당 정보의 유저가 없습니다.");
    }

    private String decodeHeader(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        return new String(decodedBytes);
    }

    private AuthInfo makeAuthInfo(String decodedString) {
        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];
        return new AuthInfo(email, password);
    }

    private boolean isStartsWithBasic(String header) {
        return header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }

}
