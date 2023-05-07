package cart.global.exception.product;

import cart.global.exception.common.CartException;
import cart.global.exception.common.ExceptionStatus;

public class ProductNotFoundException extends CartException {

    public ProductNotFoundException() {
        super(ExceptionStatus.NOT_FOUND_PRODUCT);
    }
}
