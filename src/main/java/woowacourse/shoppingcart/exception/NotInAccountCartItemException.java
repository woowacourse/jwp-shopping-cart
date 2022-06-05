package woowacourse.shoppingcart.exception;

public class NotInAccountCartItemException extends RuntimeException {
    public NotInAccountCartItemException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotInAccountCartItemException(final String msg) {
        super(msg);
    }
}
