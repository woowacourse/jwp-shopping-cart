package cart.dao;

import cart.entity.CartItemEntity;

import java.util.List;

public interface CartItemDao {

    List<CartItemEntity> selectAllByMemberId(final Long memberId);

    long insert(final CartItemEntity cartItemEntity);

}
