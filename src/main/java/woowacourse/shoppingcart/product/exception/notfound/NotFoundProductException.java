package woowacourse.shoppingcart.product.exception.notfound;

import woowacourse.shoppingcart.exception.ErrorCode;
import woowacourse.shoppingcart.exception.NotFoundException;

public class NotFoundProductException extends NotFoundException {

    public NotFoundProductException() {
        super(ErrorCode.GENERAL_NOT_FOUND, "상품이 존재하지 않습니다.");
    }
}
