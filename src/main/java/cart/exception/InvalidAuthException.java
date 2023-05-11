package cart.exception;

public class InvalidAuthException extends RuntimeException {

    public InvalidAuthException(final String errorMessage) {
        super(errorMessage);
    }
}
