package cart.auth;

import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationParser {

    private static final String KEYWORD = "Basic ";
    private static final String DELIMITER = ":";
    private static final int VALID_CREDENTIAL_SIZE = 2;
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final String EMPTY = "";

    public boolean isNotValid(final String authorizationHeader) {
        return !authorizationHeader.startsWith(KEYWORD) ||
                parseCredential(authorizationHeader).length != VALID_CREDENTIAL_SIZE;
    }

    private String[] parseCredential(final String authorizationHeader) {
        final String credential = authorizationHeader.replace(KEYWORD, EMPTY);
        return decodeBase64(credential).split(DELIMITER);
    }

    private String decodeBase64(final String credential) {
        return new String(Base64.getDecoder().decode(credential));
    }

    public Credential parse(final String authorizationHeader) {
        final String[] credential = parseCredential(authorizationHeader);
        return new Credential(credential[EMAIL_INDEX], credential[PASSWORD_INDEX]);
    }
}

