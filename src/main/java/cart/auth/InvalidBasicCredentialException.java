package cart.auth;

public class InvalidBasicCredentialException extends RuntimeException {

    public InvalidBasicCredentialException(final String authorizationHeader) {
        super("올바른 Basic 형식의 인증정보가 아닙니다. 입력값: " + authorizationHeader);
    }
}
