package cart.dao.cart;

import java.util.List;

public interface CartDao {

    List<Long> findAllByMemberId(final Long memberId);
}
