package woowacourse.shoppingcart.exception.user;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class DuplicateUserException extends ShoppingCartException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";
    private static final int ERROR_CODE = 1001;

    public DuplicateUserException() {
        super(ERROR_CODE, MESSAGE);
    }
}
