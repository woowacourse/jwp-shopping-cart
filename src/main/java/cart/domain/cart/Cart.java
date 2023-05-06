package cart.domain.cart;

import cart.domain.user.User;
import java.util.List;
import java.util.Objects;

public class Cart {
    private final User user;
    private final CartProducts cartProducts;

    public Cart(User user, CartProducts cartProducts) {
        this.user = user;
        this.cartProducts = cartProducts;
    }

    public static Cart of(User user, List<CartProduct> cartProducts) {
        return new Cart(user, new CartProducts(cartProducts));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    public User getUser() {
        return user;
    }

    public List<CartProduct> getCartItems() {
        return cartProducts.getCartItems();
    }
}
