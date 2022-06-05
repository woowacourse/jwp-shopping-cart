package woowacourse.shoppingcart.application.exception;

public final class InvalidCartItemException extends ValidatedException {
    public InvalidCartItemException() {
        this("유효하지 않은 장바구니입니다.");
    }

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
