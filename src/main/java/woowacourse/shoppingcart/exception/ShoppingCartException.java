package woowacourse.shoppingcart.exception;

public abstract class ShoppingCartException extends RuntimeException {

    private final int errorCode;

    public ShoppingCartException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
