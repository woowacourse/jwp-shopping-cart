package woowacourse.shoppingcart.exception;

public class NotInCustomerCartItemException extends RuntimeException {
    public NotInCustomerCartItemException() {
        super("일치하는 장바구니 아이템이 없습니다.");
    }
}
