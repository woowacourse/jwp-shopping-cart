package woowacourse.shoppingcart.domain.cartItem;

import java.util.Objects;
import woowacourse.shoppingcart.exception.format.InvalidQuantityException;

public class Quantity {
    private final int value;

    public Quantity(int value) {
        validateQuantity(value);
        this.value = value;
    }

    private void validateQuantity(int value) {
        if (value <= 0) {
            throw new InvalidQuantityException();
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
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

    @Override
    public String toString() {
        return "Quantity{" +
                "value=" + value +
                '}';
    }
}
