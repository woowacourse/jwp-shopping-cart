package woowacourse.shoppingcart.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("이메일 형식이 올바르지 않습니다.");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
