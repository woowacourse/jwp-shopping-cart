package woowacourse.shoppingcart.exception;

public class NotFoundCartItemException extends NotFoundException {

    public NotFoundCartItemException() {
        this("유효하지 않은 장바구니입니다.");
    }

    public NotFoundCartItemException(final String msg) {
        super(msg);
    }
}
