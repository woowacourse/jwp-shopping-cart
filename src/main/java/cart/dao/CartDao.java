package cart.dao;

import cart.domain.cart.Cart;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    Long insert(final Cart cart);

    List<Cart> findAllByMemberId(long memberId);

    Optional<Cart> findByMemberIdAndProductId(long memberId, long productId);

    void delete(final long memberId, final long productId);
}
