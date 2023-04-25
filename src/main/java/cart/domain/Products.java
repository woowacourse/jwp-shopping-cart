package cart.domain;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = List.copyOf(products);
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }
}
