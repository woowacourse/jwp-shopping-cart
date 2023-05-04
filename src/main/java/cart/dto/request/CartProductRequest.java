package cart.dto.request;

import javax.validation.constraints.NotNull;

public class CartProductRequest {

    @NotNull
    private Long productId;

    public CartProductRequest() {
    }

    public CartProductRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
