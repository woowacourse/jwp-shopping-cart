package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Integer insert(ProductEntity productEntity);

    void update(ProductEntity productEntity);

    void deleteById(Long id);

    Optional<ProductEntity> findById(Long id);

    List<ProductEntity> findAll();
}
