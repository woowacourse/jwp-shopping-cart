package cart.persistence.dao;

import cart.persistence.entity.CartProductEntity;
import java.util.List;

public interface CartProductDao {

    long save(CartProductEntity cartProductEntity);

    List<CartProductEntity> findAllByUserId();
}
