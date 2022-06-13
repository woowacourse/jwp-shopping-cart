package woowacourse.shoppingcart.exception;

public class InvalidCartItemException extends DomainException {

    private static final String MESSAGE = "유효하지 않은 장바구니입니다.";

    public InvalidCartItemException() {
        this(MESSAGE);
    }

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
