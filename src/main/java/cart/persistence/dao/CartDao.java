package cart.persistence.dao;

import cart.persistence.entity.CartEntity;
import cart.persistence.entity.CartProductEntity;

import java.util.List;

public interface CartDao extends Dao<CartEntity> {
    List<CartProductEntity> findProductsByUser(final String email);
}
