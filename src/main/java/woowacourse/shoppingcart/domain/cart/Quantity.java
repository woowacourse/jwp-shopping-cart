package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.exception.InvalidFormException;

public class Quantity {

    private static final int MIN_QUANTITY = 0;

    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_QUANTITY) {
            throw InvalidFormException.fromName("수량");
        }
    }

    public int getValue() {
        return value;
    }
}
