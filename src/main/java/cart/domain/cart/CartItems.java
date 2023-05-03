package cart.domain.cart;

import cart.domain.product.Product;

import java.util.Collections;
import java.util.List;

public class CartItems {

    private final List<Product> cartItems;

    public CartItems(final List<Product> cartItems) {
        this.cartItems = cartItems;
    }

    public void add(final Product product) {
        this.cartItems.add(product);
    }

    public Product remove(final Product product) {
        int index = this.cartItems.indexOf(product);
        return this.cartItems.remove(index);
    }

    public boolean contains(final Product product) {
        return this.cartItems.contains(product);
    }

    public Product getLastCartItem() {
        return this.cartItems.get(cartItems.size() - 1);
    }

    public List<Product> getCartItems() {
        return Collections.unmodifiableList(this.cartItems);
    }
}
