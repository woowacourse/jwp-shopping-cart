package cart.exception;

public abstract class ProductException extends RuntimeException {
    protected ProductException(String message) {
        super(message);
    }
}
