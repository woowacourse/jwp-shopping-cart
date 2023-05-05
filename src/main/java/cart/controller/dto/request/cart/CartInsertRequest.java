package cart.controller.dto.request.cart;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartInsertRequest {

    private final int productId;

    @JsonCreator
    public CartInsertRequest(final int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
