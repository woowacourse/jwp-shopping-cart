package woowacourse.shoppingcart.dto.response;

import java.util.List;

public class ProductsResponse {

    private List<ProductResponse> products;

    private ProductsResponse() {}

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductsResponse from(final List<ProductResponse> products) {
        return new ProductsResponse(products);
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
