package woowacourse.shoppingcart.exception.badrequest;

public class InvalidProductIdException extends BadRequestException {

    private static final int ERROR_CODE = 1000;
    private static final String MESSAGE = "물품이 존재하지 않습니다.";

    public InvalidProductIdException() {
        super(ERROR_CODE, MESSAGE);
    }
}
