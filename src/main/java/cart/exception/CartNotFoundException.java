package cart.exception;

public class CartNotFoundException extends NotFoundException {
    public CartNotFoundException(final String message) {
        super(message);
    }
}
