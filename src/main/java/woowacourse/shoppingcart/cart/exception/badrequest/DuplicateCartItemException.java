package woowacourse.shoppingcart.cart.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class DuplicateCartItemException extends BadRequestException {

    public DuplicateCartItemException() {
        super(ErrorCode.DUPLICATE_CART_ITEM, "중복된 상품입니다.");
    }
}
