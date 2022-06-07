package woowacourse.shoppingcart.exception;

public class NotInCustomerCartItemException extends RuntimeException {
    
    public NotInCustomerCartItemException() {
        super("장바구니에 존재하지 않는 아이템이 있습니다.");
    }
}
