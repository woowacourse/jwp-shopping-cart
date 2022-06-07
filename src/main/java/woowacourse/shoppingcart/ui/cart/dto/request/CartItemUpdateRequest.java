package woowacourse.shoppingcart.ui.cart.dto.request;

public class CartItemUpdateRequest {

    private Long cartItemId;
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(final Long cartItemId, final int quantity) {
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
