package cart.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(final String message) {
        super(message);
    }
}
