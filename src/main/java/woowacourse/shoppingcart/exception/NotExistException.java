package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class NotExistException extends ShoppingCartException {

    public NotExistException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
