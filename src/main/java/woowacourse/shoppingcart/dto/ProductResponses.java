package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponses {

    private List<ProductResponse> products;

    private ProductResponses() {
    }

    public ProductResponses(final List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductResponses from(final List<Product> products) {
        return new ProductResponses(products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList()));
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
