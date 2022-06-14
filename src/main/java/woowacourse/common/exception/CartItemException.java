package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class CartItemException extends CustomException {

    public CartItemException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
