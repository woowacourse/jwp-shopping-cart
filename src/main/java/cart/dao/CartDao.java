package cart.dao;

import cart.dao.entity.Cart;

public interface CartDao {

    Long save(final Cart cart);

    int delete(Long userId, Long productId);

    int deleteByProductId(final Long productId);
}
