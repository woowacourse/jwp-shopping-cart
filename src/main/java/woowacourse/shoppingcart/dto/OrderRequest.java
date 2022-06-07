package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull(message = "카트 id를 입력하세요.")
    @JsonProperty(value = "cart_id")
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
