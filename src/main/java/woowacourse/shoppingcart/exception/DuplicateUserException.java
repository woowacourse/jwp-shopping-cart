package woowacourse.shoppingcart.exception;

public class DuplicateUserException extends ShoppingCartException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";
    private static final int ERROR_CODE = 1001;

    public DuplicateUserException() {
        super(ERROR_CODE, MESSAGE);
    }
}
