package cart.cart.dao;

import cart.cart.domain.Cart;

public interface CartDao {

    Cart findById(final Long memberId);
}
