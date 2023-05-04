package cart.repository.dao;

import cart.repository.entity.CartEntity;
import java.util.List;

public interface CartDao {

    Long save(final CartEntity cartEntity);

    List<CartEntity> findByMemberId(final Long memberId);
}
