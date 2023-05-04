package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.User;

public class Cart {
    private final User user;
    private final Product product;

    public Cart(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }
}
