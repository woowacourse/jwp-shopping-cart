package woowacourse.auth.exception;

import woowacourse.shoppingcart.exception.InvalidInputException;

public class NoSuchEmailException extends InvalidInputException {

    private static final String MESSAGE = "존재하지 않는 이메일입니다.";

    public NoSuchEmailException() {
        super(MESSAGE);
    }
}
