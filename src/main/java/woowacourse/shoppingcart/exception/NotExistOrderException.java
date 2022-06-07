package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class NotExistOrderException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public NotExistOrderException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
