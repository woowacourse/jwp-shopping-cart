package cart.global.exception;

public class CartNotFoundException extends CartException{

    public CartNotFoundException() {
        super(ExceptionStatus.NOT_FOUND_CART.getMessage());
    }
}
