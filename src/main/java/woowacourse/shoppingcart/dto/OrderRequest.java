package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull
    private Long cartItemId;

    private OrderRequest() {
    }

    public OrderRequest(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }
}
