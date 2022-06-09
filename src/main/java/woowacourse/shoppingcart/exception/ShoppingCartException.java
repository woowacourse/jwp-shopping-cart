package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class ShoppingCartException extends RuntimeException {

    private ErrorResponse errorResponse;

    public ShoppingCartException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
