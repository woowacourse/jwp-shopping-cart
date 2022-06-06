package woowacourse.shoppingcart.cart.dto;

public class QuantityChangingRequest {

    private final int quantity;

    public QuantityChangingRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
