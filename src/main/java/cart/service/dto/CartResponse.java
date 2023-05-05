package cart.service.dto;

import java.util.List;

public class CartResponse {

    private final int productCount;

    private final List<ProductResponse> productResponses;

    public CartResponse(int productCount, List<ProductResponse> productResponses) {
        this.productCount = productCount;
        this.productResponses = productResponses;
    }

    public int getProductCount() {
        return productCount;
    }

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }
}
