package cart.service.dto;

import java.util.List;

public class CartResponse {

    private final List<ProductResponse> productResponses;

    public CartResponse(List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }
}
