package cart.dao;

import cart.domain.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemDao {

    List<CartItem> selectAllByMemberId(final Long memberId);

    Optional<CartItem> selectById(final Long id);

    long insert(final CartItem cartItem);

    int deleteByIdAndMemberId(final long id, final long memberId);

}
