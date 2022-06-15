package woowacourse.shoppingcart.dto.cart;

public class CartItemUpdateRequest {
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
