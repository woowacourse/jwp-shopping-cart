package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsResponse {

    private final List<Product> products;

    public ProductsResponse(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public static ProductsResponse from(List<Product> products) {
        return new ProductsResponse(products);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
