package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.User;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final CartId cartId;
    private final User user;
    private final CartProducts cartProducts;

    public Cart(final CartId cartId, final User user, final List<CartProduct> cartProducts) {
        this(cartId, user, new CartProducts(cartProducts));
    }

    public Cart(final User user) {
        this(new CartId(), user, new CartProducts());
    }

    public Cart(final CartId cartId, final User user, final CartProducts cartProducts) {
        this.cartId = cartId;
        this.user = user;
        this.cartProducts = cartProducts;
    }

    public void addProduct(final Product product) {
        cartProducts.add(product);
    }

    public void deleteProduct(final Long cartProductId) {
        cartProducts.delete(cartProductId);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cart cart = (Cart) o;
        return cartId.equals(cart.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}
