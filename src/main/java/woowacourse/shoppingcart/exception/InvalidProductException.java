package woowacourse.shoppingcart.exception;

public class InvalidProductException extends ShoppingCartException {
    private static final String MESSAGE = "중복된 물품입니다.";
    private static final int ERROR_CODE = 1101;

    public InvalidProductException() {
        super(ERROR_CODE, MESSAGE);
    }
}
