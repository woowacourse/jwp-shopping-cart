package woowacourse.shoppingcart.exception;

public class InvalidOrderException extends DomainException {

    private static final String MESSAGE = "유효하지 않은 주문입니다.";

    public InvalidOrderException() {
        this(MESSAGE);
    }

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
