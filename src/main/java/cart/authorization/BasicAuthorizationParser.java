package cart.authorization;

import java.util.Base64;

public class BasicAuthorizationParser {

    private static final String BASIC_TYPE = "Basic";
    private final String decodedCredential;

    public BasicAuthorizationParser(final String authorization) {
        validate(authorization);
        this.decodedCredential = extractDecodedCredential(authorization);
    }

    private void validate(final String authorization) {
        if (!authorization.startsWith(BASIC_TYPE)) {
            throw new IllegalArgumentException("Basic 인증의 Authorization 헤더값이 아닙니다.");
        }
    }

    private String extractDecodedCredential(final String authorization) {
        final String encodedCredential = authorization.split(" ")[1];
        final byte[] decode = Base64.getDecoder().decode(encodedCredential);
        return new String(decode);
    }

    public String getEmail() {
        return decodedCredential.split(":")[0];
    }

    public String getPassword() {
        return decodedCredential.split(":")[1];
    }
}
