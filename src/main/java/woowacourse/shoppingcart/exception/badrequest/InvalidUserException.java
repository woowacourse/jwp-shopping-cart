package woowacourse.shoppingcart.exception.badrequest;

import woowacourse.shoppingcart.exception.badrequest.BadRequestException;

public class InvalidUserException extends BadRequestException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";
    private static final int ERROR_CODE = 1002;

    public InvalidUserException() {
        super(ERROR_CODE, MESSAGE);
    }
}
