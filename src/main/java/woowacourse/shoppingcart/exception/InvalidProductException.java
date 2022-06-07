package woowacourse.shoppingcart.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException() {
        this("장바구니에 해당하는 상품이 없습니다.");
    }

    public InvalidProductException(final String msg) {
        super(msg);
    }
}
