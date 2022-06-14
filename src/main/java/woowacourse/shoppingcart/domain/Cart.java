package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.Product;

import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
