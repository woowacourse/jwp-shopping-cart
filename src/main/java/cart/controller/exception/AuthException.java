package cart.controller.exception;

public class AuthException extends IllegalArgumentException {

    public AuthException(final String message) {
        super(message);
    }
}
