package woowacourse.shoppingcart.application.exception;

public final class InvalidOrderException extends ValidatedException {
    public InvalidOrderException() {
        this("유효하지 않은 주문입니다.");
    }

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
