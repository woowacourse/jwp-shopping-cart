package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.user.UserId;
import java.util.Optional;

public interface CartRepository {

    Cart save(Cart cart);

    Optional<Cart> findByUserId(UserId userId);
}
