package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.application.exception.InvalidQuantityException;

import java.util.Objects;

public class Quantity {
    private final int value;

    public Quantity(final int value) {
        validateMinimum(value);
        this.value = value;
    }

    private void validateMinimum(final int value) {
        if (value < 0) {
            throw new InvalidQuantityException();
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
