package woowacourse.shoppingcart.exception;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        this("유효하지 않은 입력입니다.");
    }

    public ValidationException(final String msg) {
        super(msg);
    }
}
