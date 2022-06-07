package woowacourse.shoppingcart.exception;

public class DuplicateCartItemException extends ShoppingCartException {

    private static final String MESSAGE = "중복된 물품입니다.";
    private static final int ERROR_CODE = 1101;

    public DuplicateCartItemException() {
        super(ERROR_CODE, MESSAGE);
    }
}
