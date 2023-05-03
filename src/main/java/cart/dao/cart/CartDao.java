package cart.dao.cart;

import java.util.List;

public interface CartDao {

    void save(final Long memberId, final Long productId);

    List<Long> findAllByMemberId(final Long memberId);
}
