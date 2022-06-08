package woowacourse.shoppingcart.product.support.exception;

import woowacourse.support.exception.ShoppingCartException;

public class ProductException extends ShoppingCartException {

    public ProductException(final ProductExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
