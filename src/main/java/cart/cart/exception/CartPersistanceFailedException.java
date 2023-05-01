package cart.cart.exception;

public class CartPersistanceFailedException extends Exception {
    public CartPersistanceFailedException(String message) {
        super(message);
    }

    public CartPersistanceFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
