package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.IllegalFormException;

public class Quantity {

    private final int value;

    public Quantity(final int value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(int value) {
        if (value < 0 || value > 99) {
            throw new IllegalFormException("수량");
        }
    }

    public int getValue() {
        return value;
    }
}
