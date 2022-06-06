package woowacourse.shoppingcart.exception.notfound;

public abstract class NotFoundException extends RuntimeException {

    private final int errorCode;

    public NotFoundException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
