package cart.domain;

import cart.exception.InvalidProductIdException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final Long memberId;
    private final List<Product> products;

    public Cart(Long memberId) {
        this(memberId, Collections.emptyList());
    }

    public Cart(Long memberId, List<Product> products) {
        if (products == null || products.stream().anyMatch(Product::isIdNull)) {
            throw new InvalidProductIdException();
        }
        this.memberId = memberId;
        this.products = new ArrayList<>(products);
    }

    public void add(Product product) {
        if (product.isIdNull()) {
            throw new InvalidProductIdException();
        }
        products.add(product);
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    public void remove(Product product) {
        products.remove(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(memberId, cart.memberId) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, products);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "memberId=" + memberId +
                ", products=" + products +
                '}';
    }
}
