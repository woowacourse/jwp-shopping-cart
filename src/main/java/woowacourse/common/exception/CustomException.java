package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class CustomException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public CustomException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
