package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class ProductNotFoundException extends CartException {

    public static final CartException EXCEPTION = new ProductNotFoundException();

    public ProductNotFoundException() {
        super(new ErrorCode(404, "PRODUCT-404-1", "해당 상품을 찾을 수 없습니다."));
    }

}
