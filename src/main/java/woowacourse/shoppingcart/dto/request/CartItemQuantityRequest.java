package woowacourse.shoppingcart.dto.request;

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
