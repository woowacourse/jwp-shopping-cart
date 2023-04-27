package cart.exception;

import cart.ErrorCode;

public class BusinessIllegalArgumentException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public BusinessIllegalArgumentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
