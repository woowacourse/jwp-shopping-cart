package woowacourse.shoppingcart.domain.cart;

public class Quantity {

    private final int quantity;

    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValue() {
        return quantity;
    }
}
