package woowacourse.shoppingcart.exception;

import woowacourse.exception.dto.ErrorResponse;

public class NotExistCartItemException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public NotExistCartItemException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
