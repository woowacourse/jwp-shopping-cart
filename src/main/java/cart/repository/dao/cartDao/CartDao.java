package cart.repository.dao.cartDao;

import cart.entity.Cart;

import java.util.List;

public interface CartDao {

    Long save(final Cart cart);

    List<Long> findAllProductIdByMemberId(final Long memberId);

    int delete(final Long memberId, final Long productId);
}
