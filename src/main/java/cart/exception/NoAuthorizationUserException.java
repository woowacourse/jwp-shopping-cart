package cart.exception;

public class NoAuthorizationUserException extends RuntimeException {
    public NoAuthorizationUserException(final String message) {
        super(message);
    }
}
