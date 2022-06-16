package woowacourse.shoppingcart.order.exception.notfound;

import woowacourse.shoppingcart.exception.ErrorCode;
import woowacourse.shoppingcart.exception.NotFoundException;

public class NotFoundCartItemException extends NotFoundException {

    public NotFoundCartItemException() {
        super(ErrorCode.NOT_FOUND_CART_ITEM, "장바구니에 포함되지 않은 상품이 존재합니다.");
    }
}
