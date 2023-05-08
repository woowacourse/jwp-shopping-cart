package cart.cart.domain;

import cart.product.domain.Product;
import cart.user.domain.CartUser;
import java.util.List;

public class Cart {
    private final CartUser cartUser;
    private final List<Product> products;

    public Cart(final CartUser cartUser, final List<Product> products) {
        this.cartUser = cartUser;
        this.products = products;
    }

    public CartUser getCartUser() {
        return cartUser;
    }

    public List<Product> getProducts() {
        return products;
    }
}
