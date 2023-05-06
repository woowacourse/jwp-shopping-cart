package cart.domain;

import cart.domain.product.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final Long id;
    private final List<Product> products;

    private Cart(Long id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public static Cart createEmpty(Long id) {
        return new Cart(id, new ArrayList<>());
    }

    public static Cart createWithProducts(Long id, List<Product> products) {
        return new Cart(id, new ArrayList<>(products));
    }

    public void add(Product product) {
        this.products.add(product);
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
