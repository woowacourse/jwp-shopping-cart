package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsResponse {

    private List<ProductResponse> products;

    public ProductsResponse() {
    }

    public ProductsResponse(List<ProductResponse> productResponses) {
        this.products = productResponses;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
