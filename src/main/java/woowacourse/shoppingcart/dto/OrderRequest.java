package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull(message = "장바구니 번호는 빈값일 수 없습니다.")
    private final Long cartId;
    @Min(value = 1, message = "수량은 1이상이어야 합니다.")
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
