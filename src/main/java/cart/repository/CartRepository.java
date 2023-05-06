package cart.repository;

import cart.domain.Cart;
import cart.domain.CartProduct;
import cart.domain.User;

public interface CartRepository {

    Cart findByUser(User user);

    void removeCartItem(Long CartItemId);

    void addCartItem(Cart cart, CartProduct cartProduct);
}
