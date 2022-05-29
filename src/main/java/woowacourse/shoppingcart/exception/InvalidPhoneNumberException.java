package woowacourse.shoppingcart.exception;

public class InvalidPhoneNumberException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "핸드폰 번호 형식이 맞지 않습니다.";

    public InvalidPhoneNumberException() {
        this(ERROR_MESSAGE);
    }

    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
