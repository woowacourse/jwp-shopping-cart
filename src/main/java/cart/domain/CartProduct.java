package cart.domain;

import java.util.Objects;

public class CartProduct {
    private Long id;
    private final Product product;

    public CartProduct(Product product) {
        this.product = product;
    }

    public CartProduct(Long id, Product product) {
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
        CartProduct cartProduct = (CartProduct) o;
        return Objects.equals(id, cartProduct.id);
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
