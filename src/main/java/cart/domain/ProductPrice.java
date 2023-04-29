package cart.domain;

import java.util.Objects;

public class ProductPrice {

    private static final int MIN_PRICE = 0;

    private final int value;

    public ProductPrice(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < MIN_PRICE) {
            throw new IllegalArgumentException("가격은 " + MIN_PRICE + "원 이상이어야 합니다.");
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
        final ProductPrice that = (ProductPrice) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
