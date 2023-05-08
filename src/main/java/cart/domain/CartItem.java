package cart.domain;

import java.util.Objects;

public class CartItem {

    private final Integer id;
    private final Product product;

    public CartItem(Product product) {
        this(null, product);
    }

    public CartItem(Integer id, Product product) {
        this.id = id;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CartItem cartItem = (CartItem)o;

        return Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return product != null ? product.hashCode() : 0;
    }

    public Integer getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}
