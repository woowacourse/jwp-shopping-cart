package woowacourse.shoppingcart.cart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.product.domain.Product;

public class Cart {

    private Long id;
    private Product product;
    private Quantity quantity;

    private Cart() {
    }

    public Cart(final Long id, final Product product) {
        this(id, product, 1);
    }

    public Cart(final Long id, final Product product, final int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = Quantity.from(quantity);
    }

    public Cart changeQuantity(final int quantity) {
        return new Cart(id, product, quantity);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getValue();
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
        return Objects.equals(id, cart.id) && Objects.equals(product, cart.product)
                && Objects.equals(quantity, cart.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
