package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponses {

    private List<ProductResponse> products;

    private ProductResponses() {
    }

    private ProductResponses(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductResponses from(List<Product> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new ProductResponses(productResponses);
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "ProductResponses{" +
                "products=" + products +
                '}';
    }
}
