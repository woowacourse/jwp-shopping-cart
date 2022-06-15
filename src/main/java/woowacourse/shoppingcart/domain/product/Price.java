package woowacourse.shoppingcart.domain.product;

public class Price {

    private final int value;

    public Price(int price) {
        validateNegativePrice(price);
        this.value = price;
    }

    private void validateNegativePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수가 될 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
