package woowacourse.shoppingcart.domain.cartitem;

public class Quantity {
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
        if (quantity < 1 || quantity > 99) {
            throw new IllegalArgumentException("개수의 범위를 벗어났습니다.");
        }
    }

    public int value() {
        return quantity;
    }
}
