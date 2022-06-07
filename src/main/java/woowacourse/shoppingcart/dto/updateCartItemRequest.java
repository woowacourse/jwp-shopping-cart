package woowacourse.shoppingcart.dto;

public class updateCartItemRequest {

    private Long cartItemId;
    private int quantity;

    public updateCartItemRequest() {
    }

    public updateCartItemRequest(Long cartItemId, int quantity) {
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
