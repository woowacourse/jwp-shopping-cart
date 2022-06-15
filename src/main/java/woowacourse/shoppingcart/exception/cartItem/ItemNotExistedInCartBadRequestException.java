package woowacourse.shoppingcart.exception.cartItem;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class ItemNotExistedInCartBadRequestException extends ShoppingCartBadRequestException {

    public static final int ERROR_CODE = 1102;
    private static final String MESSAGE = "장바구니에 상품이 존재하지 않습니다.";

    public ItemNotExistedInCartBadRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
