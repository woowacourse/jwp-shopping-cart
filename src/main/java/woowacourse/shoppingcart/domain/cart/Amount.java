package woowacourse.shoppingcart.domain.cart;

public class Amount {

    private final int value;

    public Amount(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 음수일 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
