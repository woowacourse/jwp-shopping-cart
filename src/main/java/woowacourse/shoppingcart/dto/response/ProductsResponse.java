package woowacourse.shoppingcart.dto.response;

import java.util.List;

public class ProductsResponse {

    private final int totalCount;
    private final List<ProductResponse> products;

    public ProductsResponse(int totalCount, List<ProductResponse> products) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
