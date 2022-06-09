package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class InputFormatException extends ShoppingCartException {

    public InputFormatException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
