package cart.dao;

import cart.domain.entity.CartItem;

import java.util.List;

public interface CartItemDao {

    List<CartItem> selectAllByMemberId(final Long memberId);

    long insert(final CartItem cartItem);

    int deleteByIdAndMemberId(final long id, final long memberId);

}
