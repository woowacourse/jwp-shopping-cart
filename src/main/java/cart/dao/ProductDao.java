package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Long save(ProductEntity productEntity);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Long id);

    void update(ProductEntity productEntity);

    void delete(Long id);
}
