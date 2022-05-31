package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class AuthException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public AuthException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
