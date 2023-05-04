package cart.argumentresolver.basicauthorization;

public final class BasicAuthorizationExtractor {

    private static final String CREDENTIAL_DELIMITER = ":";
    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final int VALID_AUTH_INFO_LENGHTH = 2;

    private final String credential;

    public BasicAuthorizationExtractor(final String credential) {
        validateCredential(credential);
        this.credential = credential;
    }

    private void validateCredential(String credential) {
        if (credential.split(CREDENTIAL_DELIMITER, -1).length != VALID_AUTH_INFO_LENGHTH) {
            throw new IllegalArgumentException("잘못된 형식의 인증 정보 입니다.");
        }
    }

    public String extractUsername() {
        return credential.split(CREDENTIAL_DELIMITER, -1)[USERNAME_INDEX];
    }

    public String extractPassword() {
        return credential.split(CREDENTIAL_DELIMITER, -1)[PASSWORD_INDEX];
    }
}
