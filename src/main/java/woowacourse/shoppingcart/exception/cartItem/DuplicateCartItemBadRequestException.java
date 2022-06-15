package woowacourse.shoppingcart.exception.cartItem;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class DuplicateCartItemBadRequestException extends ShoppingCartBadRequestException {

    private static final String MESSAGE = "중복된 물품입니다.";
    private static final int ERROR_CODE = 1101;

    public DuplicateCartItemBadRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
