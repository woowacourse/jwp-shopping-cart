package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Quantity {

    private static final int MINIMUM_QUANTITY = 1;
    private final int quantity;

    public Quantity(int quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private static void validate(int quantity) {
        if (quantity < MINIMUM_QUANTITY) {
            throw new IllegalArgumentException("올바르지 않은 상품 수량 형식입니다.");
        }
    }

    public int getValue() {
        return quantity;
    }
}
