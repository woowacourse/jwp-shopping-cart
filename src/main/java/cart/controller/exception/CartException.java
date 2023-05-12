package cart.controller.exception;

public class CartException extends IllegalArgumentException {

    public CartException(final String message) {
        super(message);
    }
}
