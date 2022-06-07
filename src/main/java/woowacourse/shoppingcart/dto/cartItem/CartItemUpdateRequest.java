package woowacourse.shoppingcart.dto.cartItem;

public class CartItemUpdateRequest {
    private Long cartItemId;
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(Long cartItemId, int quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
