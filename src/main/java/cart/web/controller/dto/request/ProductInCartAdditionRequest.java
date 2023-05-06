package cart.web.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductInCartAdditionRequest {
    private final Long productId;

    @JsonCreator
    public ProductInCartAdditionRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
