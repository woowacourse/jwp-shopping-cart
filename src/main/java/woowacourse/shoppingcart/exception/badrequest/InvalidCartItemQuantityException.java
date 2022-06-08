package woowacourse.shoppingcart.exception.badrequest;

public class InvalidCartItemQuantityException extends BadRequestException {

    private static final int ERROR_CODE = 1100;
    private static final String MESSAGE = "잘못된 형식입니다.";

    public InvalidCartItemQuantityException() {
        super(ERROR_CODE, MESSAGE);
    }
}
