package woowacourse.shoppingcart.dto;

public class CartItemQuantityRequest {

    private int quantity;

    private CartItemQuantityRequest() {
    }

    public CartItemQuantityRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
