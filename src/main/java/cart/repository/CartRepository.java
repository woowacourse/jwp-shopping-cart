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

    public void save(final User user, final Cart cart) {
        cartItemRepository.save(user.getId(), cart.getItems());
    }

    public Cart getCartOf(final User user) {
        List<CartItem> cartItems = cartItemRepository.getItemsOf(user);
        return new Cart(cartItems);
    }
}
