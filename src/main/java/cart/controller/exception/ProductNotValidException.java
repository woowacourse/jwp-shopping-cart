package cart.controller.exception;

public class ProductNotValidException extends IllegalArgumentException{

    public ProductNotValidException(final String message) {
        super(message);
    }
}
