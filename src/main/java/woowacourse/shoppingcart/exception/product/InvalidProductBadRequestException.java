package woowacourse.shoppingcart.exception.product;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class InvalidProductBadRequestException extends ShoppingCartBadRequestException {
    private static final String MESSAGE = "중복된 물품입니다.";
    private static final int ERROR_CODE = 1101;

    public InvalidProductBadRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
