package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
