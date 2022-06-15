package woowacourse.shoppingcart.domain.order;

public class Quantity {

    private static final int LIMIT = 99;

    private final int value;

    public Quantity(int quantity) {
        validateQuantityLimit(quantity);
        this.value = quantity;
    }

    private void validateQuantityLimit(int quantity) {
        if (quantity > LIMIT) {
            throw new IllegalArgumentException("상품의 수는 99개를 초과할 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
