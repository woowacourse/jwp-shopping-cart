package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull(message = "장바구니id는 필수 항목입니다.")
    private final Long cartId;

    @NotNull(message = "수량은 필수 항목입니다.")
    @Min(value = 1,message = "수량은 1개 미만일 수 없습니다.")
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
