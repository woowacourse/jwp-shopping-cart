package woowacourse.shoppingcart.exception.badrequest;

public class InvalidEmailException extends BadRequestException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";
    private static final int ERROR_CODE = 1001;

    public InvalidEmailException() {
        super(ERROR_CODE, MESSAGE);
    }
}
