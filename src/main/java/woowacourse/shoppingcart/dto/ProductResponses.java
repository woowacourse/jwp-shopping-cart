package woowacourse.shoppingcart.dto;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponses {

    private List<Product> products;

    private ProductResponses() {
    }

    public ProductResponses(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
