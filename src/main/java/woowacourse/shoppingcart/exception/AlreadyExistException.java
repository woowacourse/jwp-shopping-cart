package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class AlreadyExistException extends RuntimeException {
    private ErrorResponse errorResponse;

    public AlreadyExistException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
