package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.user.User;

public interface CartRepository {

    Cart save(Cart cart);

    Cart update(Cart cart);

    Cart findByUser(User user);
}
