package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsResponse {

    public ProductsResponse(List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    private final List<ProductResponse> productResponses;

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }
}
