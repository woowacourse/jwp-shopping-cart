package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull
    private Long cartId;

    private OrderRequest() {
    }

    public OrderRequest(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }
}
