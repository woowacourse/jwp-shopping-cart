package woowacourse.shoppingcart.domain.cart;

class Amount {

    private final int value;

    Amount(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 음수일 수 없습니다.");
        }
    }

    int getValue() {
        return value;
    }
}
