package woowacourse.shoppingcart.cart.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class NoExistCartItemException extends BadRequestException {

    public NoExistCartItemException() {
        super(ErrorCode.NOT_EXIST_ITEM_IN_CART, "장바구니에 상품이 존재하지 않습니다.");
    }
}
