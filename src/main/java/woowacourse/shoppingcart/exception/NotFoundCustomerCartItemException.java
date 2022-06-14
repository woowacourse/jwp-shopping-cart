package woowacourse.shoppingcart.exception;

public class NotFoundCustomerCartItemException extends NotFoundException {

    public NotFoundCustomerCartItemException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotFoundCustomerCartItemException(final String msg) {
        super(msg);
    }
}
