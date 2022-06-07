package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidQuantityException;

public class Quantity {
    private final int quantity;

    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    private void validate(int quantity) {
        if (quantity < 0) {
            throw new InvalidQuantityException();
        }
    }

    public int getQuantity() {
        return quantity;
    }
}
