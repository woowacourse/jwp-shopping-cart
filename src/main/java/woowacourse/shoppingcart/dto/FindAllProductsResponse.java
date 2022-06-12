package woowacourse.shoppingcart.dto;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public class FindAllProductsResponse {

    private final List<Product> products;

    public FindAllProductsResponse(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
