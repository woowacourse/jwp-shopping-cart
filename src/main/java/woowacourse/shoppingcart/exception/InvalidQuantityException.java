package woowacourse.shoppingcart.exception;

public class InvalidQuantityException extends ShoppingCartException {
    private static final String MESSAGE = "잘못된 형식입니다.";
    private static final int ERROR_CODE = 1100;

    public InvalidQuantityException() {
        super(ERROR_CODE, MESSAGE);
    }
}
