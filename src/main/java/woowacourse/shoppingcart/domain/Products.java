package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
