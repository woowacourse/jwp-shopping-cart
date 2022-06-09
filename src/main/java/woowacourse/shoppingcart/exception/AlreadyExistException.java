package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class AlreadyExistException extends ShoppingCartException {

    public AlreadyExistException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
