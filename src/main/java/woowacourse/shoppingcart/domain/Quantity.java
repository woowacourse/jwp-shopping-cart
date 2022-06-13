package woowacourse.shoppingcart.domain;

import java.util.Objects;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
