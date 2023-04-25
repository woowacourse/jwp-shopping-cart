package cart.dto.response;

import java.util.List;

public class ProductsResponse {

    private List<ProductResponse> productResponses;

    public ProductsResponse(List<ProductResponse> productResponses) {
        this.productResponses = List.copyOf(productResponses);
    }

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }
}
