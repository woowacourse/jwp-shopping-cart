package cart.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddCartItemRequest {

    @NotNull
    @Positive
    private Long productId;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
