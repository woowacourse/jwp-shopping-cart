package cart.dao.cart;

import java.util.List;

public interface CartDao {

    void insert(final Long memberId, final Long productId);

    List<Long> findAllProductIdByMemberId(final Long memberId);

    void deleteByMemberIdAndProductId(final Long memberId, final Long productId);
}
