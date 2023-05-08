package cart.domain.cart;

import cart.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
