package woowacourse.shoppingcart.exception;

public class InvalidCartException extends RuntimeException {
    public InvalidCartException() {
        this("장바구니에 해당하는 상품이 없습니다.");
    }

    public InvalidCartException(final String msg) {
        super(msg);
    }
}
