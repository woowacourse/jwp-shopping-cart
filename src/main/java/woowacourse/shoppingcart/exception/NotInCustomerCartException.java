package woowacourse.shoppingcart.exception;

public class NotInCustomerCartException extends RuntimeException {
    public NotInCustomerCartException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotInCustomerCartException(final String msg) {
        super(msg);
    }
}
