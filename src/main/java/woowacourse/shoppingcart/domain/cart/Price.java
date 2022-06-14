package woowacourse.shoppingcart.domain.cart;

class Price {

    private final int value;

    Price(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }

    int getValue() {
        return value;
    }
}
