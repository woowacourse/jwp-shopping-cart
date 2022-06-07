package woowacourse.shoppingcart.exception.badrequest;

public class NotInCustomerCartItemException extends BadRequestException {

    public NotInCustomerCartItemException() {
        super("1101", "장바구니 상품이 존재하지 않습니다.");
    }
}
