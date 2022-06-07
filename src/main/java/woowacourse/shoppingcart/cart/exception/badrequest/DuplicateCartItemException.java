package woowacourse.shoppingcart.cart.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class DuplicateCartItemException extends BadRequestException {

    public DuplicateCartItemException() {
        super("1101", "중복된 상품입니다.");
    }
}
