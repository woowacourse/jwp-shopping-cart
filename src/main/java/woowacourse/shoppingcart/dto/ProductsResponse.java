package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsResponse {

    private final int totalCount;
    private final List<ProductResponse> products;

    public ProductsResponse(final int totalCount, final List<ProductResponse> products) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public static ProductsResponse from(final List<ProductResponse> responses) {
        return new ProductsResponse(responses.size(), responses);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
