package woowacourse.shoppingcart.exception;

public class InvalidEmailException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "이메일 형식이 맞지않습니다.";

    public InvalidEmailException() {
        this(ERROR_MESSAGE);
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
