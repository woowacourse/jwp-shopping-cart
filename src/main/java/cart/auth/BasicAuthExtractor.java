package cart.auth;

import cart.auth.excpetion.AuthorizeException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int CREDENTIAL_SIZE = 2;

    public AuthInfo extract(final AuthHeader header) {
        validateBasic(header);

        final String decodeHeaderValue = decode(header);
        final String[] credentials = decodeHeaderValue.split(DELIMITER);

        validateCredentials(credentials);

        final String email = credentials[0];
        final String password = credentials[1];

        return new AuthInfo(email, password);
    }

    private void validateBasic(final AuthHeader header) {
        if (!header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new AuthorizeException("인증 정보가 다릅니다.");
        }
    }

    private String decode(AuthHeader header) {
        final String authorizedHeaderValue = getBasicAuthValue(header.getHeader());
        return new String(Base64.decodeBase64(authorizedHeaderValue));
    }

    private static void validateCredentials(String[] credentials) {
        if (credentials.length != CREDENTIAL_SIZE) {
            throw new AuthorizeException("인증 정보가 다릅니다.");
        }
    }

    private String getBasicAuthValue(final String header) {
        return header.substring(BASIC_TYPE.length()).trim();
    }
}
