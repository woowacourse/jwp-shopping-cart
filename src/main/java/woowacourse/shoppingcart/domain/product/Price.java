package woowacourse.shoppingcart.domain.product;

import java.util.Objects;
import woowacourse.shoppingcart.exception.format.InvalidPriceException;

public class Price {
    private static final int MIN_VALUE = 10;

    private final int value;

    public Price(int value) {
        validatePrice(value);
        this.value = value;
    }

    private void validatePrice(int value) {
        if (value < 0 || value % MIN_VALUE != 0) {
            throw new InvalidPriceException();
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
        Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                '}';
    }
}
