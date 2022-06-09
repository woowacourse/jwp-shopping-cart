package woowacourse.shoppingcart.domain;

public class Amount {

    private static final int MIN_VALUE = 0;

    private final int value;

    public Amount(int value) {
        validateAmount(value);

        this.value = value;
    }

    private void validateAmount(int value) {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException("개수는 0 미만일 수 없습니다.");
        }
    }

    public boolean isMoreThan(Amount other) {
        return this.value >= other.value;
    }

    public int getValue() {
        return value;
    }
}
