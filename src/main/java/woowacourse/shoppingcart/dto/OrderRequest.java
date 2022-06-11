package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull
    private final Long cartId;
    @Min(value = 0, message = "수량은 0개 이상이어야 합니다. : ${validatedValue}")
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
