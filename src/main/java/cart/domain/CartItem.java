package cart.domain;

import java.util.Objects;

public class CartItem {
    private Long id;
    private final Product product;

    public CartItem(Product product) {
        this.product = product;
    }

    public CartItem(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}
