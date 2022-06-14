package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class OutOfStockException extends RuntimeException{
    private ErrorResponse errorResponse;

    public OutOfStockException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
