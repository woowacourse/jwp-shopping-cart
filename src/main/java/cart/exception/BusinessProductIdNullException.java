package cart.exception;

public class BusinessProductIdNullException extends NullPointerException {
    private final ErrorCode errorCode;

    public BusinessProductIdNullException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
