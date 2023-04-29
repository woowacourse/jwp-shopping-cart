package cart.domain;

import java.util.Objects;

public class Price {

    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 1_000_000_000;

    private final int value;

    public Price(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < LOWER_BOUND || value > UPPER_BOUND) {
            throw new IllegalArgumentException("가격은 10억 이하의 음이 아닌 정수여야 합니다.");
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
        final Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
