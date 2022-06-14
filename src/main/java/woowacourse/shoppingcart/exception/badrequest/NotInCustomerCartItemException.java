package woowacourse.shoppingcart.exception.badrequest;

public class NotInCustomerCartItemException extends BadRequestException {

    private static final int ERROR_CODE = 1102;
    private static final String MESSAGE = "장바구니에 상품이 존재하지 않습니다.";

    public NotInCustomerCartItemException() {
        super(ERROR_CODE, MESSAGE);
    }
}
