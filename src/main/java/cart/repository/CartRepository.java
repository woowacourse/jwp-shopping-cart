package cart.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.User;

@Repository
public class CartRepository {

    private final CartItemRepository cartItemRepository;

    public CartRepository(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void save(final Cart cart) {
        cartItemRepository.save(cart.getOwner().getId(), cart.getItems());
    }

    public Cart getCartOf(final User user) {
        List<CartItem> cartItems = cartItemRepository.getItemsOf(user);
        return new Cart(user, cartItems);
    }
}
