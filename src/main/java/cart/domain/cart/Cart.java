package cart.domain.cart;

import cart.domain.product.Product;
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
        this(new CartId(), user, new CartProducts());
    }

    public void addProduct(final Product product) {
        cartProducts.add(product);
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
