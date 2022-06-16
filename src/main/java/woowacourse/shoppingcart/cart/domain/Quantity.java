package woowacourse.shoppingcart.cart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.cart.exception.badrequest.InvalidQuantityException;

public class Quantity {

    private static final int MIN_QUANTITY = 1;
    private static final Quantity MIN_VALUE_INSTANCE = new Quantity(MIN_QUANTITY);

    private final int value;

    private Quantity(final int value) {
        this.value = value;
    }

    public static Quantity from(final int value) {
        validate(value);
        if (value == MIN_QUANTITY) {
            return MIN_VALUE_INSTANCE;
        }
        return new Quantity(value);
    }

    private static void validate(final int value) {
        if (value < MIN_QUANTITY) {
            throw new InvalidQuantityException();
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
