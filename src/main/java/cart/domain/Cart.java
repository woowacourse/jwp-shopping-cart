package cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart() {
        this(Collections.emptyList());
    }

    public Cart(List<Product> products) {
        if (products.stream().distinct().count() != products.size()) {
            throw new IllegalArgumentException();
        }
        this.products = new ArrayList<>(products);
    }

    public void add(Product product) {
        products.add(product);
    }

    public void delete(Product product) {
        products.stream()
                .filter(it -> it.equals(product))
                .findFirst()
                .ifPresent(products::remove);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
