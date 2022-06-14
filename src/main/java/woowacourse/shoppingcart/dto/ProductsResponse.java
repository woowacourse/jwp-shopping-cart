package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsResponse {
    private List<ProductResponse> products;

    private ProductsResponse() {
    }

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
