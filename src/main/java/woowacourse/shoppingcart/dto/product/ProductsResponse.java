package woowacourse.shoppingcart.dto.product;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductResponse.ProductResponseNested;

public class ProductsResponse {

    private List<ProductResponseNested> products;

    private ProductsResponse() {
    }

    public ProductsResponse(final List<ProductResponseNested> products) {
        this.products = products;
    }

    public static ProductsResponse from(final List<Product> products) {
        return new ProductsResponse(products.stream()
                .map(ProductResponseNested::from)
                .collect(Collectors.toList()));
    }

    public List<ProductResponseNested> getProducts() {
        return products;
    }
}
