package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @JsonProperty(value = "cart_id")
    @NotNull
    private Long cartId;

    private OrderRequest() {
    }

    public OrderRequest(final Long cartId) {
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }

}
