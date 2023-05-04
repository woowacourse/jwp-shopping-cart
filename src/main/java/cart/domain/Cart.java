package cart.domain;

import cart.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
