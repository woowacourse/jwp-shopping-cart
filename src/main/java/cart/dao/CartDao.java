package cart.dao;

import cart.dao.entity.Cart;

public interface CartDao {

    Long save(final Cart cart);

    int delete(final Long id);
}
