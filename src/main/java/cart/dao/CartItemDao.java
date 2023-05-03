package cart.dao;

import cart.entity.CartItemEntity;

public interface CartItemDao {
    long insert(final CartItemEntity cartItemEntity);

}
