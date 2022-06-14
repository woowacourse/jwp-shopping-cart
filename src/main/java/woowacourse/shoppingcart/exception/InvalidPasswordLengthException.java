package woowacourse.shoppingcart.exception;

public class InvalidPasswordLengthException extends DomainException {

    private static final String MESSAGE = "비밀번호는 8자리 이상, 15자리 이하여야 합니다.";

    public InvalidPasswordLengthException() {
        super(MESSAGE);
    }
}
