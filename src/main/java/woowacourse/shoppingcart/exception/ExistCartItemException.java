package woowacourse.shoppingcart.exception;

public class ExistCartItemException extends RuntimeException {
    public ExistCartItemException() {
        this("이미 장바구니에 상품이 담겨있습니다!");
    }

    public ExistCartItemException(final String msg) {
        super(msg);
    }
}
