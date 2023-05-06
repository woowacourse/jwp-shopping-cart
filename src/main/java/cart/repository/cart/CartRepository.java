package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;

public interface CartRepository {

    Cart findByNo(Long cartNo);

    void removeCartItem(Long CartItemId);

    void addCartItem(Cart cart, CartProduct cartProduct);
}
