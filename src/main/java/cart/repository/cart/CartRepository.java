package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.user.User;
import java.util.Optional;

public interface CartRepository {

    Cart save(Cart cart);

    Optional<Cart> findByUser(User user);

    Cart update(Cart cart);
}
