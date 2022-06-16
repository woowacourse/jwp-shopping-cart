package woowacourse.shoppingcart.cart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.product.domain.Product;

public class CartItem {

    private Long id;
    private Product product;
    private Quantity quantity;

    private CartItem() {
    }

    public CartItem(final Long id, final Product product) {
        this(id, product, 1);
    }

    public CartItem(final Long id, final Product product, final int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = Quantity.from(quantity);
    }

    public CartItem changeQuantity(final int quantity) {
        return new CartItem(id, product, quantity);
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
        final CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && Objects.equals(product, cartItem.product)
                && Objects.equals(quantity, cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
