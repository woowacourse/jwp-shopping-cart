package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull(groups = Request.id.class)
    private Long cartId;
    @Min(value = 0, groups = Request.allProperties.class)
    private int quantity;

    public OrderRequest() {
    }

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
