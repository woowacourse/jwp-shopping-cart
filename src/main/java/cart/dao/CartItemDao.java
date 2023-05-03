package cart.dao;

import cart.entity.CartItemEntity;
import cart.entity.MemberEntity;

public interface CartItemDao {
    long insert(final CartItemEntity cartItemEntity);

}
