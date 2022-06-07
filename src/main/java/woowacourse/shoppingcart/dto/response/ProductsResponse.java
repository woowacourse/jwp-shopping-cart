package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductsResponse {
    private List<ProductResponse> products;

    public ProductsResponse() {
    }

    private ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductsResponse from(List<Product> products) {
        return new ProductsResponse(products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList()));
    }

    public List<ProductResponse> getProducts() {
        return List.copyOf(products);
    }
}
