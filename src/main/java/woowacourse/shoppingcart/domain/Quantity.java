package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.QuantityRangeException;

public class Quantity {

    private static final int MINIMUM_QUANTITY = 0;
    private static final int MAXIMUM_QUANTITY = 99;

    private final int value;

    public Quantity(final int value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(int value) {
        if (value < MINIMUM_QUANTITY || value > MAXIMUM_QUANTITY) {
            throw new QuantityRangeException();
        }
    }

    public int getValue() {
        return value;
    }
}
