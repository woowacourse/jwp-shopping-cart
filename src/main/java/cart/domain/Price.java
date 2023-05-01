package cart.domain;

public class Price {

    private static final int AMOUNT_LIMIT = 0;

    private final int value;

    public Price(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int price) {
        if (price > AMOUNT_LIMIT) {
            return;
        }
        throw new IllegalArgumentException("가격은 0보다 커야합니다.");
    }

    public int getValue() {
        return value;
    }
}
