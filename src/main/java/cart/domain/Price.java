package cart.domain;

import java.util.Objects;

public class Price {

    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 1_000_000_000;
    private final Integer value;

    public Price(final Integer value) {
        validateEmpty(value);
        validateNegative(value);
        validateMax(value);
        this.value = value;
    }

    private void validateEmpty (final Integer value) {
        if (value == null) {
           throw new IllegalArgumentException("가격은 반드시 있어야합니다.");
        }
    }

    private void validateNegative(final Integer value) {
        if (value < MIN_PRICE) {
            throw new IllegalArgumentException("가격은 0이상이어야 합니다.");
        }
    }

    private void validateMax(final Integer value) {
        if (value > MAX_PRICE) {
            throw new IllegalArgumentException("가격은 10억이하여야 합니다.");
        }
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value);
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
