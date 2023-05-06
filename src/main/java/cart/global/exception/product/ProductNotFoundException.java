package cart.global.exception.product;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(final String message) {
        super(message);
    }
}
