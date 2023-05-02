package cart.authorization;

import java.util.Base64;

final class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String CREDENTIAL_DELIMITER = ":";
    private static final String AUTHORIZATION_DELIMITER = " ";

    private final String authorization;

    public BasicAuthorizationExtractor(final String authorization) {
        validateBasicAuthorization(authorization);
        this.authorization = authorization;
    }

    private void validateBasicAuthorization(final String authorization) {
        if (!authorization.startsWith(BASIC_TYPE)) {
            throw new IllegalArgumentException("Basic 인증의 Authorization 헤더값이 아닙니다.");
        }
    }

    private String decodeCredential(final String authorization) {
        final String encodedCredential = authorization.split(AUTHORIZATION_DELIMITER)[1];
        final byte[] decodedByteArray = Base64.getDecoder().decode(encodedCredential);
        final String decodedCredential = new String(decodedByteArray);
        validateCredential(decodedCredential);
        return decodedCredential;
    }

    private void validateCredential(String decodedCredential) {
        if (decodedCredential.split(CREDENTIAL_DELIMITER, -1).length != 2) {
            throw new IllegalArgumentException("잘못된 형식의 인증 정보 입니다.");
        }
    }

    public String extractEmail() {
        return decodeCredential(authorization).split(CREDENTIAL_DELIMITER, -1)[0];
    }

    public String extractPassword() {
        return decodeCredential(authorization).split(CREDENTIAL_DELIMITER, -1)[1];
    }
}
