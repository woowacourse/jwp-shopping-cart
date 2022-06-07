package woowacourse.shoppingcart.cart.exception.notfound;

import woowacourse.shoppingcart.exception.notfound.NotFoundException;

public class NotFoundCartException extends NotFoundException {

    public NotFoundCartException() {
        super("2001", "장바구니에 해당 상품이 존재하지 않습니다.");
    }
}
