package woowacourse.shoppingcart.cart.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class InvalidQuantityException extends BadRequestException {

    public InvalidQuantityException() {
        super("1000", "수량이 유효하지 않습니다.");
    }
}
