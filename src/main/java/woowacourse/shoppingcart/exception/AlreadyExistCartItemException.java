package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class AlreadyExistCartItemException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public AlreadyExistCartItemException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
