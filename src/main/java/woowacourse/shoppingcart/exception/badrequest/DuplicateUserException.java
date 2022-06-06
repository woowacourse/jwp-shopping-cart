package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateUserException extends BadRequestException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";
    private static final int ERROR_CODE = 1001;

    public DuplicateUserException() {
        super(ERROR_CODE, MESSAGE);
    }
}
