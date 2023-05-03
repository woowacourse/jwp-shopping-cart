package cart.domain;

import java.util.List;
import java.util.Objects;

public class Cart {
    private final User user;
    private final CartItems cartItems;

    public Cart(User user, CartItems cartItems) {
        this.user = user;
        this.cartItems = cartItems;
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeCartItem(Long cartItemId) {
        cartItems.remove(cartItemId);
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

    public List<CartItem> getCartItems() {
        return cartItems.getCartItems();
    }
}
