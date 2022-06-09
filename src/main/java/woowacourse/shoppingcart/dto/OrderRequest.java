package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Orders;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull
    private Long cartId;
    @Min(0)
    private int quantity;

    private OrderRequest() {
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
