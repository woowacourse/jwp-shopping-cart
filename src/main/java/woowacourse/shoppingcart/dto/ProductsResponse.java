package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<Product> products) {
        this.products = products.stream()
            .map(ProductResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
