package woowacourse.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<ProductResponse> products) {
        this.products = new ArrayList<>(products);
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
