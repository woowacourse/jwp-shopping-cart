package cart.exception.notfound;

import cart.exception.product.ProductException;

public class ProductNotFoundException extends NotFoundException {

    private static final String NOT_FOUND_ERROR_MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(NOT_FOUND_ERROR_MESSAGE);
    }
}
