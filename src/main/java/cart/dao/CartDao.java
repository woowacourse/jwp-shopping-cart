package cart.dao;

import cart.domain.cart.Cart;
import java.util.Optional;

public interface CartDao {

    Long insert(final Cart cart);

    Optional<Cart> findByMemberIdAndProductId(final long memberId, final long productId);

    void updateQuantity(final Cart cart);
}
