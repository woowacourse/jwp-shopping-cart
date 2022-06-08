package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.IllegalQuantityException;

public class Quantity {

    private static final int MIN_VALUE = 1;

    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_VALUE) {
            throw new IllegalQuantityException();
        }
    }

    public int getValue() {
        return value;
    }
}
