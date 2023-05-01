package cart.dao;

import cart.dao.entity.Cart;

public interface CartDao {

    Long save(final Cart cart);
}
