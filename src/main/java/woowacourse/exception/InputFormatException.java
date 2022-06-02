package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class InputFormatException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public InputFormatException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
