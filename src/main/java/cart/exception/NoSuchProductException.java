package cart.exception;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(final String message) {
        super(message);
    }
}
