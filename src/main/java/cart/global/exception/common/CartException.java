package cart.global.exception.common;

public class CartException extends RuntimeException {
    private final String message;
    private final ExceptionStatus exceptionStatus;

    public CartException(final ExceptionStatus exceptionStatus) {
        this.message = exceptionStatus.getMessage();
        this.exceptionStatus = exceptionStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ExceptionStatus getExceptionStatus() {
        return exceptionStatus;
    }
}
