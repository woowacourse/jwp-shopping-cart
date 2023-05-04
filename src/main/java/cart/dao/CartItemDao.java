package cart.dao;

import cart.domain.entity.CartItemEntity;

import java.util.List;

public interface CartItemDao {

    List<CartItemEntity> selectAllByMemberId(final Long memberId);

    long insert(final CartItemEntity cartItemEntity);

    int deleteById(final long id);

}
