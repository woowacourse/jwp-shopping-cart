package woowacourse.shoppingcart.exception.badrequest;

public class InvalidNicknameException extends BadRequestException {

    private static final int ERROR_CODE = 1000;
    private static final String MESSAGE = "잘못된 별명입니다.";

    public InvalidNicknameException() {
        super(ERROR_CODE, MESSAGE);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
