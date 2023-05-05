package cart.controller.dto.request;

import javax.validation.constraints.NotNull;

public class CartItemCreationRequest {
    @NotNull(message = "아이디가 비어있습니다.")
    private Long productId;

    public CartItemCreationRequest() {

    }

    public CartItemCreationRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
