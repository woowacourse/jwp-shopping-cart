package woowacourse.shoppingcart.dto.order;

import javax.validation.constraints.NotNull;

public class OrderSaveRequest {
    @NotNull
    private Long cartItemId;

    private OrderSaveRequest() {
    }

    public OrderSaveRequest(final Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }
}
