package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public class OrderRequest {

    @JsonProperty("cart_id")
    private final Long cartId;
    @Min(0)
    private final int quantity;

    public OrderRequest(final Long cartId, final int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
