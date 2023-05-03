package cart.domain;

public class Price {
    private static final int MAX_PRICE_VALUE = 1_000_000_000;

    private final long value;

    public Price(long value) {
        validatePrice(value);
        this.value = value;
    }

    private void validatePrice(long price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수 혹은 빈 값이 될 수 없습니다.");
        }
        if (price > MAX_PRICE_VALUE) {
            throw new IllegalArgumentException("가격은 10억을 초과할 수 없습니다.");
        }
    }

    public long getValue() {
        return value;
    }
}
