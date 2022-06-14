package woowacourse.shoppingcart.exception;

public class NotMatchPasswordException extends RuntimeException {

    public NotMatchPasswordException() {
        this("비밀번호가 일치하지 않습니다.");
    }

    public NotMatchPasswordException(final String message) {
        super(message);
    }
}
