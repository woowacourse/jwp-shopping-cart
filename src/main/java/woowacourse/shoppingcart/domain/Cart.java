package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.QuantityNotMatchException;

public class Cart {

    private final Long id;
    private final Quantity quantity;
    private final Product product;

    public Cart(final Long id, final Quantity quantity, final Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public void checkQuantity(final int quantity) {
        if (!this.quantity.equals(new Quantity(quantity))) {
            throw new QuantityNotMatchException();
        }
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(quantity, cart.quantity)
                && Objects.equals(product, cart.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, product);
    }
}
