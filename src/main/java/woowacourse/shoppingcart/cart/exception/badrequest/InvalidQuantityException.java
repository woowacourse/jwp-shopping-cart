package woowacourse.shoppingcart.cart.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class InvalidQuantityException extends BadRequestException {

    public InvalidQuantityException() {
        super(ErrorCode.INVALID_CART_REQUEST_FORM, "수량이 유효하지 않습니다.");
    }
}
