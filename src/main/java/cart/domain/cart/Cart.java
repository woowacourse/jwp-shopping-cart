package cart.domain.cart;

import cart.domain.user.User;
import java.util.List;

public class Cart {

    private final CartId cartId;
    private final User user;
    private final CartProducts cartProducts;

    public Cart(final CartId cartId, final User user, final CartProducts cartProducts) {
        this.cartId = cartId;
        this.user = user;
        this.cartProducts = cartProducts;
    }

    public Cart(final CartId cartId, final User user, final List<CartProduct> cartProducts) {
        this(cartId, user, new CartProducts(cartProducts));
    }

    public Cart(final User user) {
        this(null, user, new CartProducts());
    }

    public Cart(final CartId cartId, final Cart other) {
        this(cartId, other.user, other.cartProducts);
    }

    public CartId getCartId() {
        return cartId;
    }

    public User getUser() {
        return user;
    }

    public CartProducts getCartProducts() {
        return cartProducts;
    }
}
