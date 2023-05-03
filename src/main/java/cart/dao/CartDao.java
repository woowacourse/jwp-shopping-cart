package cart.dao;

import cart.domain.cart.Cart;
import java.util.List;

public interface CartDao {

    Long insert(final Cart cart);

    List<Cart> findAllByMemberId(long memberId);
}
