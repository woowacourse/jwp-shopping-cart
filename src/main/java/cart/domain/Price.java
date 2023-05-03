package cart.domain;

public class Price {

    private final int value;

    public Price(final int value) {
        validateIsPositive(value);
        this.value = value;
    }

    private void validateIsPositive(final int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("가격은 양수만 가능합니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
