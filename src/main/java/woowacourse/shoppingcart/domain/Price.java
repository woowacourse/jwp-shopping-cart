package woowacourse.shoppingcart.domain;

public class Price {
    private final int value;

    public Price(int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }

    }

    public int getValue() {
        return value;
    }
}
