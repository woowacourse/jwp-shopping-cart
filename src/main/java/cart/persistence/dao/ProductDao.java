package cart.persistence.dao;

import cart.persistence.entity.CartProductEntity;
import cart.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductDao extends Dao<ProductEntity> {
    List<CartProductEntity> findProductsByUser(final String email);
}
