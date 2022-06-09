package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class OutOfStockException extends ShoppingCartException {

    public OutOfStockException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
