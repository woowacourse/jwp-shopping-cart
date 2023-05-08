package cart.persistence.dao;

import cart.persistence.entity.CartProductEntity;
import java.util.List;

public interface CartProductDao {

    List<CartProductEntity> findAllByUserId();

    long save(CartProductEntity cartProductEntity);

    boolean delete(CartProductEntity cartProductEntity);
}
