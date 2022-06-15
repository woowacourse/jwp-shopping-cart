package woowacourse.shoppingcart.exception;

public class InvalidEmailFormatException extends InvalidInputException {

    private static final String MESSAGE = "올바른 이메일 형식이 아닙니다.";

    public InvalidEmailFormatException() {
        super(MESSAGE);
    }
}
