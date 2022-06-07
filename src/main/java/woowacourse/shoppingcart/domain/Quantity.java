package woowacourse.shoppingcart.domain;

public class Quantity {

    private final int quantity;

    public Quantity(int quantity) {
        validateOverZero(quantity);
        this.quantity = quantity;
    }

    private void validateOverZero(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 최소 1개여야 합니다.");
        }
    }

    public int getQuantity() {
        return quantity;
    }
}
