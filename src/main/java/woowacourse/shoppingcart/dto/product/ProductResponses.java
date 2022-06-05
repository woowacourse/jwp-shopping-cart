package woowacourse.shoppingcart.dto.product;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductResponse.ProductResponseNested;

public class ProductResponses {

    private List<ProductResponseNested> products;

    private ProductResponses() {
    }

    public ProductResponses(final List<ProductResponseNested> products) {
        this.products = products;
    }

    public static ProductResponses from(final List<Product> products) {
        return new ProductResponses(products.stream()
                .map(ProductResponseNested::from)
                .collect(Collectors.toList()));
    }

    public List<ProductResponseNested> getProducts() {
        return products;
    }
}
