package cart.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull
    private Long productId;

    public CartRequest() {
    }

    public CartRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
