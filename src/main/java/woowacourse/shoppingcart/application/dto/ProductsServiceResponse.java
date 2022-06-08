package woowacourse.shoppingcart.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductsServiceResponse {

    private final int totalCount;
    private final List<ProductDetailServiceResponse> products;

    public ProductsServiceResponse(final int totalCount, final List<ProductDetailServiceResponse> products) {
        this.totalCount = totalCount;
        this.products = products;
    }

    public static ProductsServiceResponse from(final int productCount, final List<Product> products) {
        final List<ProductDetailServiceResponse> productResponses = products.stream()
                .map(ProductDetailServiceResponse::from)
                .collect(Collectors.toList());
        return new ProductsServiceResponse(productCount, productResponses);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<ProductDetailServiceResponse> getProducts() {
        return products;
    }
}
