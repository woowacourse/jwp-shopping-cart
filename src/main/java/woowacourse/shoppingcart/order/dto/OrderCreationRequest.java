package woowacourse.shoppingcart.order.dto;

import javax.validation.constraints.NotNull;

public class OrderCreationRequest {

    @NotNull
    private final Long productId;

    public OrderCreationRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
