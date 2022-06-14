package woowacourse.auth.exception;

import woowacourse.shoppingcart.exception.InvalidInputException;

public class PasswordNotMatchException extends InvalidInputException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
