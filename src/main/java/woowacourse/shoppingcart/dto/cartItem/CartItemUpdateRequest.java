package woowacourse.shoppingcart.dto.cartItem;

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
