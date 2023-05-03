package cart.global.exception;

public class ProductNotFoundException extends CartException {

    public ProductNotFoundException() {
        super(ExceptionStatus.NOT_FOUND_PRODUCT.getMessage());
    }
}
