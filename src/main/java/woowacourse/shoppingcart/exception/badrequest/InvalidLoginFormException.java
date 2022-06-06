package woowacourse.shoppingcart.exception.badrequest;

public class InvalidLoginFormException extends BadRequestException {

    private static final int ERROR_CODE = 1002;
    private static final String MESSAGE = "잘못된 아이디 또는 비밀번호입니다.";

    public InvalidLoginFormException() {
        super(ERROR_CODE, MESSAGE);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
