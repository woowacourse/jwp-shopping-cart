package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull
    private final Long cartId;
    @Min(0)
    private final Integer quantity;

    public OrderRequest(final Long cartId, final Integer quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
