package cart.controller.dto;

import java.util.List;

public class CartResponse {

    private final List<ProductResponse> productResponses;

    public CartResponse(final List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }
}
