package woowacourse.shoppingcart.exception;

public class InvalidCartItemException extends RuntimeException {
    public InvalidCartItemException() {
        this("장바구니에 해당하는 상품이 없습니다.");
    }

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
