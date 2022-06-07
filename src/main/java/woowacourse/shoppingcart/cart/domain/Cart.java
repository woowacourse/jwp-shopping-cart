package woowacourse.shoppingcart.cart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.cart.exception.badrequest.InvalidQuantityException;
import woowacourse.shoppingcart.product.domain.Product;

public class Cart {

    private static final int MIN_QUANTITY = 1;

    private Long id;
    private Product product;
    private int quantity;

    private Cart() {
    }

    public Cart(final Long id, final Product product) {
        this(id, product, MIN_QUANTITY);
    }

    public Cart(final Long id, final Product product, final int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart changeQuantity(final int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidQuantityException();
        }
        return new Cart(id, product, quantity);
    }

    public boolean hasSameProduct(final Product product) {
        return this.product.equals(product);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
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
        return quantity == cart.quantity && Objects.equals(id, cart.id) && Objects.equals(product,
                cart.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
