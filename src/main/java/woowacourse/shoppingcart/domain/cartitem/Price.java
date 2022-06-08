package woowacourse.shoppingcart.domain.cartitem;

public class Price {

    private final int value;

    public Price(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
