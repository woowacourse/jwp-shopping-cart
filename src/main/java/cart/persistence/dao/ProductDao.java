package cart.persistence.dao;

import cart.persistence.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    long save(ProductEntity productEntity);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void deleteById(long id);

    Optional<ProductEntity> findById(Long id);
}
