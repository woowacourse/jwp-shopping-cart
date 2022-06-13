package woowacourse.shoppingcart.domain;

public class Quantity {
    private final int value;

    public Quantity(int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 양수일 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
