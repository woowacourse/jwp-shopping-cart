package woowacourse.shoppingcart.ui.product.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.application.dto.ProductsServiceResponse;

public class ProductsResponse {

    private int totalCount;
    private List<ProductResponse> products;

    public ProductsResponse() {
    }

    public ProductsResponse(final int totalCount, final List<ProductResponse> products) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public static ProductsResponse from(final ProductsServiceResponse products) {
        final List<ProductResponse> productResponses = products.getProducts()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new ProductsResponse(products.getTotalCount(), productResponses);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
