package cart.excpetion.cart;

public class DuplicateCartItemException extends CartException {
    public DuplicateCartItemException(final String message) {
        super(message);
    }
}
