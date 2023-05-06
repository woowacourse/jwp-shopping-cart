package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.user.User;

public interface CartRepository {

    Cart findByUser(User user);

    void removeCartItem(Long CartItemId);

    void addCartItem(Cart cart, CartProduct cartProduct);
}
