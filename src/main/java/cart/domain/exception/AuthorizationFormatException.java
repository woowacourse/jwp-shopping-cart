package cart.domain.exception;

public class AuthorizationFormatException extends RuntimeException {

    public AuthorizationFormatException() {
    }

    public AuthorizationFormatException(String message) {
        super(message);
    }
}
