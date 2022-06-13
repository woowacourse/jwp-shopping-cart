package woowacourse.shoppingcart.exception;

public class NotInCustomerCartItemException extends RuntimeException {

    public NotInCustomerCartItemException() {
        this("[ERROR] 장바구니 아이템이 없습니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg);
    }
}
