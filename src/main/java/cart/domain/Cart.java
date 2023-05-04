package cart.domain;

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

    // TODO: 2023-05-02 프레임워크에 의존하지 않고 products nonNUll검사할 방법?
    public Cart(Long memberId, List<Product> products) {
        if (products.stream().anyMatch(Product::isIdNull)) {
            // TODO: 2023-05-02 예외처리
        }
        this.memberId = memberId;
        this.products = new ArrayList<>(products);
    }

    public void add(Product product) {
        if (product.isIdNull()) {
            // TODO: 2023-05-02 예외처리
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
