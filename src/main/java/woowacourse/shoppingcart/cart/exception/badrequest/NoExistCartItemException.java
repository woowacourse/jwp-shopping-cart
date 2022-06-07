package woowacourse.shoppingcart.cart.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class NoExistCartItemException extends BadRequestException {

    public NoExistCartItemException() {
        super("1102", "장바구니에 상품이 존재하지 않습니다.");
    }
}
