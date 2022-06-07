package woowacourse.shoppingcart.dto;

public class CartItemUpdateRequest {
    private final Long cartItemId;
    private final int quantity;

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
