package cart.exception;

public class CartProductNotFoundException extends NotFoundException {

    public CartProductNotFoundException(String message) {
        super(message);
    }
}
