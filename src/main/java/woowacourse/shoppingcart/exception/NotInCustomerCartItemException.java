package woowacourse.shoppingcart.exception;

public class NotInCustomerCartItemException extends RuntimeException {
    public NotInCustomerCartItemException() {
        super("장바구니 아이템이 없습니다.");
    }
}
