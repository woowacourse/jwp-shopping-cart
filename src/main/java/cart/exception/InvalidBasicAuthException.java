package cart.exception;

public class InvalidBasicAuthException extends RuntimeException {

    public InvalidBasicAuthException(final String errorMessage) {
        super(errorMessage);
    }
}
