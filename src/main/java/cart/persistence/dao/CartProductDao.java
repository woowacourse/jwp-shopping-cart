package cart.persistence.dao;

import cart.persistence.entity.CartProductEntity;
import java.util.List;

public interface CartProductDao {

    List<CartProductEntity> findAllByMemberId(long memberId);

    long save(CartProductEntity cartProductEntity);

    boolean delete(CartProductEntity cartProductEntity);
}
