package cart.domain;

public class Price {
    private static final int MIN_PRICE_RANGE = 0;
    private static final int MAX_PRICE_RANGE = 100_000_000;
    private final int value;

    public Price(final int price) {
        validatePrice(price);
        this.value = price;
    }

    public int getValue() {
        return value;
    }

    private void validatePrice(final int price) {
        if (price < MIN_PRICE_RANGE || price > MAX_PRICE_RANGE) {
            throw new IllegalArgumentException("0원 이상 1억 이하의 가격을 입력해주세요.");
        }
    }
}
