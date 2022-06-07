package woowacourse.shoppingcart.dto;

public class CartItemQuantityRequest {
    private Long cartItemId;
    private Integer quantity;

    public CartItemQuantityRequest() {
    }

    public CartItemQuantityRequest(Long cartItemId, Integer quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
