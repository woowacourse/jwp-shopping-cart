package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateCartItemException extends BadRequestException {

    private static final int ERROR_CODE = 1000;
    private static final String MESSAGE = "중복된 물품입니다.";

    public DuplicateCartItemException() {
        super(ERROR_CODE, MESSAGE);
    }
}
