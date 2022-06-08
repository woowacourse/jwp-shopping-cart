package woowacourse.shoppingcart.dto;

public class UpdateCartItemRequest {

    private Long cartItemId;
    private int quantity;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(Long cartItemId, int quantity) {
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
