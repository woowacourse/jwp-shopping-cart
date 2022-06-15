package woowacourse.shoppingcart.exception;

public class AlreadyExistCartItemException extends RuntimeException {
    public AlreadyExistCartItemException() {
        this("이미 장바구니에 담긴 품목입니다.");
    }

    public AlreadyExistCartItemException(final String msg) {
        super(msg);
    }
}
