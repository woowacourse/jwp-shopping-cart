package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderDetailRequest {

    @NotNull
    private final Long cartId;

    @Min(value = 0, message = "수량은 0 미만이 될 수 없습니다.")
    private final Integer quantity;

    private OrderDetailRequest() {
        this(null, null);
    }

    public OrderDetailRequest(final Long cartId, final Integer quantity) {
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
