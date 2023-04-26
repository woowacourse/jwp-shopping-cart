package cart.error.exception;

public class CartException extends RuntimeException {

    private final ErrorCode errorCode;

    public CartException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
