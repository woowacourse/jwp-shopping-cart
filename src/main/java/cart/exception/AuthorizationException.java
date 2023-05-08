package cart.exception;

public class AuthorizationException extends ApiException {

    public static final String DEFAULT_AUTHORIZATION_MESSAGE = "해당 작업에 대한 권한이 존재하지 않습니다.";

    public AuthorizationException() {
        super(DEFAULT_AUTHORIZATION_MESSAGE);
    }

    public AuthorizationException(String message) {
        super(DEFAULT_AUTHORIZATION_MESSAGE + System.lineSeparator() + message);
    }
}
