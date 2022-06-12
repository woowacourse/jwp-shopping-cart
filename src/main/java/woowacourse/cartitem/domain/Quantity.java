package woowacourse.cartitem.domain;

import woowacourse.cartitem.exception.InvalidQuantityException;

public class Quantity {

    private final int value;

    public Quantity(final int value) {
        validateNegative(value);
        this.value = value;
    }

    private void validateNegative(final int value) {
        if (value < 0) {
            throw new InvalidQuantityException("수량에는 음수가 입력될 수 없습니다.");
        }
    }

    public Quantity update(final int value) {
        return new Quantity(value);
    }

    public int getValue() {
        return value;
    }
}
