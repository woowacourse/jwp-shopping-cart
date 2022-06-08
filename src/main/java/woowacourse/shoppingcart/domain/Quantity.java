package woowacourse.shoppingcart.domain;

import woowacourse.exception.InvalidQuantityException;

public class Quantity {

    private final int quantity;

    public Quantity(final int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException();
        }
    }

    public int getQuantity() {
        return quantity;
    }
}
