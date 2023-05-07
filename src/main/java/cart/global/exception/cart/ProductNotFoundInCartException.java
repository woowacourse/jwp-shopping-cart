package cart.global.exception.cart;

import cart.global.exception.common.CartException;
import cart.global.exception.common.ExceptionStatus;

public class ProductNotFoundInCartException extends CartException {
    public ProductNotFoundInCartException() {
        super(ExceptionStatus.NOT_FOUND_CART_PRODUCT.getMessage());
    }
}
