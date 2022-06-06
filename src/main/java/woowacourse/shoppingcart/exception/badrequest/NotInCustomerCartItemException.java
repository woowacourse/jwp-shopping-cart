package woowacourse.shoppingcart.exception.badrequest;

public class NotInCustomerCartItemException extends BadRequestException {

    public NotInCustomerCartItemException() {
        super("1004", "장바구니 아이템이 없습니다.");
    }
}
