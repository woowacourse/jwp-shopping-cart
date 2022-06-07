package woowacourse.shoppingcart.product.exception.notfound;

import woowacourse.shoppingcart.exception.notfound.NotFoundException;

public class NotFoundProductException extends NotFoundException {

    public NotFoundProductException() {
        super("2000", "상품이 존재하지 않습니다.");
    }
}
