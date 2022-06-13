package woowacourse.shoppingcart.cartitem.ui.dto;

import javax.validation.constraints.Min;

public class CartItemQuantityRequest {
    private Long cartItemId;

    @Min(message = "5002", value = 0)
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
