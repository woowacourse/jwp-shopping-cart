package cart.controller.exception;

public class ProductNotFoundException extends IllegalArgumentException{

    public ProductNotFoundException(final String message) {
        super(message);
    }
}
