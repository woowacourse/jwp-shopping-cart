package cart.repository;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.User;

public interface CartRepository {

    Cart findByUser(User user);

    void removeCartItem(Cart cart, CartItem removalItem);

    void addCartItem(Cart cart, CartItem cartItem);
}
