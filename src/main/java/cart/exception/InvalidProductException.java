package cart.exception;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(final String errorMessage) {
        super(errorMessage);
    }
}
