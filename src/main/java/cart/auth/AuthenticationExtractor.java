package cart.auth;

import java.util.Base64;
import java.util.regex.Pattern;

public class AuthenticationExtractor {

    private static final String BASIC_PREFIX = "Basic ";
    private static final String BASIC_DELIMITER = ":";
    private static final String EMPTY = "";
    private static final Pattern BASIC_CREDENTIAL_PATTERN = Pattern.compile("^Basic [A-Za-z0-9+/]+=*$");

    public AuthMember extractAuthInfo(String authentication) {
        validateCredentials(authentication);
        String[] emailAndPassword = extractBasicAuthInfo(authentication);
        if (emailAndPassword.length != 2) {
            throw new AuthenticationException();
        }
        String email = emailAndPassword[0];
        String password = emailAndPassword[1];
        return new AuthMember(email, password);
    }

    private void validateCredentials(String authorization) {
        validateNull(authorization);
        validateBasicAuth(authorization);
    }

    private void validateNull(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new AuthenticationException();
        }
    }

    private void validateBasicAuth(String authorization) {
        if (!BASIC_CREDENTIAL_PATTERN.matcher(authorization).matches()) {
            throw new AuthenticationException();
        }
    }

    private String[] extractBasicAuthInfo(String authorization) {
        String credentials = authorization.replace(BASIC_PREFIX, EMPTY);
        String decodedString = new String(Base64.getDecoder().decode(credentials));
        return decodedString.split(BASIC_DELIMITER);
    }
}
