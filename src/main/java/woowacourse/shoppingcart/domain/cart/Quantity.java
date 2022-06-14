package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.exception.bodyexception.ValidateException;

public class Quantity {

    private static final int MIN_VALUE = 1;

    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_VALUE) {
            throw new ValidateException("1100", "잘못된 형식입니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
