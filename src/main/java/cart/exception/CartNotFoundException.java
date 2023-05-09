package cart.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(final String message) {
        super(message);
    }

}
