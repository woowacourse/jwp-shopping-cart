package woowacourse.shoppingcart.infra.dao;

import java.util.List;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;

public interface CartDao {
    List<CartEntity> findCartsByMemberId(long memberId);

    void save(List<CartEntity> cartEntities);

    void deleteByCartIds(List<Long> cartIds);
}
