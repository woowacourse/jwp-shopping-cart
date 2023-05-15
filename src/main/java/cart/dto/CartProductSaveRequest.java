package cart.dto;

import javax.validation.constraints.NotNull;

public class CartProductSaveRequest {

    @NotNull
    private Long productId;

    public CartProductSaveRequest() {
    }

    public CartProductSaveRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
