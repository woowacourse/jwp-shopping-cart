package woowacourse.shoppingcart.domain;

public class Quantity {
    private final int value;

    public Quantity(int value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
