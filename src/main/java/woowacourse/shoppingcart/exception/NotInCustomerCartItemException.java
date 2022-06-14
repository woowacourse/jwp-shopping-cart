package woowacourse.shoppingcart.exception;

public class NotInCustomerCartItemException extends RuntimeException {
    public NotInCustomerCartItemException() {
        this("존재하지 않는 장바구니 아이템이 포함돼있습니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg);
    }
}
