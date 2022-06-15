package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.invalid.InvalidQuantityException;

public class Quantity {

    private static final int MIN = 1;
    private static final int MAX = 99;
    private static final int PLUS_VALUE = 1;

    private final int value;

    public Quantity(final int quantity) {
        validateRange(quantity);
        this.value = quantity;
    }

    private void validateRange(final int quantity) {
        if (quantity < MIN || quantity > MAX) {
            throw new InvalidQuantityException();
        }
    }

    public Quantity plus() {
        return new Quantity(value + PLUS_VALUE);
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
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
