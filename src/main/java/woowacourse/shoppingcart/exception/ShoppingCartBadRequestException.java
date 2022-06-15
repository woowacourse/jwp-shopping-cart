package woowacourse.shoppingcart.exception;

public abstract class ShoppingCartBadRequestException extends RuntimeException {

    private final int errorCode;

    public ShoppingCartBadRequestException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
