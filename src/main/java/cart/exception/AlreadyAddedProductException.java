package cart.exception;

public class AlreadyAddedProductException extends RuntimeException {

    public AlreadyAddedProductException(final String message) {
        super(message);
    }
}
