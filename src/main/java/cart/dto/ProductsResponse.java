package cart.dto;

import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductsResponse of(List<Product> products) {
        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        return new ProductsResponse(responses);
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
