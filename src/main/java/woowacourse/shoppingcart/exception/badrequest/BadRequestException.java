package woowacourse.shoppingcart.exception.badrequest;

public abstract class BadRequestException extends RuntimeException {

    private final int errorCode;

    public BadRequestException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
