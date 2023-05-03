package cart.vo;

import java.util.Objects;

public class Price {

    private static final int PRICE_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int PRICE_UPPER_BOUND_EXCLUSIVE = 1_000_000_000;
    private final int value;

    private Price(int value) {
        this.value = value;
    }

    public static Price from(int value) {
        validateRange(value);
        return new Price(value);
    }

    private static void validateRange(int value) {
        if (isInvalidRange(value)) {
            throw new IllegalStateException("올바르지 않은 가격입니다.");
        }
    }

    private static boolean isInvalidRange(int value) {
        return value < PRICE_LOWER_BOUND_EXCLUSIVE || PRICE_UPPER_BOUND_EXCLUSIVE < value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
