package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class LoginException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public LoginException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
