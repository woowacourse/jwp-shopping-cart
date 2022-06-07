package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class NotExistException extends RuntimeException{
    private ErrorResponse errorResponse;

    public NotExistException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
