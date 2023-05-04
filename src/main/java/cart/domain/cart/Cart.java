package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.CartUser;
import java.util.List;

public class Cart {
    private final CartUser cartUser;
    private final List<Product> products;

    public Cart(CartUser cartUser, List<Product> products) {
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
