package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public static ProductsResponse of(List<Product> products) {
        final List<ProductResponse> convertedProducts = products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        return new ProductsResponse(convertedProducts);
    }
}
