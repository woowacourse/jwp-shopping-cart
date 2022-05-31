package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class JoinException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public JoinException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
