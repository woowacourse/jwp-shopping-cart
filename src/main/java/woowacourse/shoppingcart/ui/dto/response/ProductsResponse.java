package woowacourse.shoppingcart.ui.dto.response;

import java.util.List;

public class ProductsResponse {
    private final long totalCount;
    private final List<ProductResponse> products;

    public ProductsResponse(final long totalCount, final List<ProductResponse> products) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public static ProductsResponse of(final long totalCount, final List<ProductResponse> productResponses) {
        return new ProductsResponse(totalCount, productResponses);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
