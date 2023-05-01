package cart.dao;

import cart.dao.entity.Cart;

import java.util.List;

public interface CartDao {

    Long save(final Cart cart);

    List<Cart> findAllByUserId(final Long id);
}
