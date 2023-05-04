package cart.exception;

public class BasicAuthException extends RuntimeException {

    public BasicAuthException(final String errorMessage) {
        super(errorMessage);
    }
}
