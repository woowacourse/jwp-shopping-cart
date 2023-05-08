package cart.common;

import cart.domain.Member;
import cart.exception.UnAuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;

public class MemberExtractor {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int BASIC_PREFIX_LENGTH = 6;

    public static Member extract(String auth) {
        if (auth == null || auth.length() < BASIC_PREFIX_LENGTH || auth.substring(BASIC_TYPE.length()).trim().equals("null")) {
            throw new UnAuthorizationException();
        }

        if ((auth.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = auth.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];
            return new Member(email, password);
        }
        throw new UnAuthorizationException();
    }
}
