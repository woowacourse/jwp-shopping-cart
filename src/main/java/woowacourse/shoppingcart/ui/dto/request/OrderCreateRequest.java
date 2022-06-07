package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderCreateRequest {
    @NotNull
    @Positive
    private Long cartId;
    @NotNull
    @Positive
    private Long quantity;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(final Long cartId, final Long quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
