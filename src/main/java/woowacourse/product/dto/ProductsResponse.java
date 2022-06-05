package woowacourse.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.product.domain.Product;

public class ProductsResponse {

    private List<ProductResponse> products;

    private ProductsResponse() {
    }

    public ProductsResponse(final List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductsResponse from(final List<Product> products) {
        final List<ProductResponse> productResponses = products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        return new ProductsResponse(productResponses);
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
