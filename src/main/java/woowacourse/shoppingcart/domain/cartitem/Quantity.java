package woowacourse.shoppingcart.domain.cartitem;

public class Quantity {

    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 99;

    private int quantity;

    public Quantity(final int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("개수의 범위를 벗어났습니다.");
        }
    }

    public int value() {
        return quantity;
    }
}
