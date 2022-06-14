package woowacourse.shoppingcart.exception;

public class InvalidAccountException extends BadRequestException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";
    private static final int ERROR_CODE = 1002;

    public InvalidAccountException() {
        super(ERROR_CODE, MESSAGE);
    }
}
