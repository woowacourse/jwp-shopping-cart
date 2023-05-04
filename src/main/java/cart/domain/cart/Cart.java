package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.User;

public class Cart {
    private final User user;
    private final Product product;
    private final Long id;

    public Cart(User user, Product product, Long id) {
        this.user = user;
        this.product = product;
        this.id = id;
    }

    public Cart(User user, Product product) {
        this(user, product, null);
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }
}
