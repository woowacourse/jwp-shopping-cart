package woowacourse.shoppingcart.exception.product;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class InvalidQuantityBadRequestException extends ShoppingCartBadRequestException {
    private static final String MESSAGE = "잘못된 수량 형식입니다.";
    private static final int ERROR_CODE = 1100;

    public InvalidQuantityBadRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
