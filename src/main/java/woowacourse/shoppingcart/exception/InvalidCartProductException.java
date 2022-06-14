package woowacourse.shoppingcart.exception;

public class InvalidCartProductException extends RuntimeException {
    public InvalidCartProductException() {
        this("유효하지 않은 장바구니 상품 갯수입니다.");
    }

    public InvalidCartProductException(final String msg) {
        super(msg);
    }
}
