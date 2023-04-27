package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Integer insert(ProductEntity productEntity);

    void update(ProductEntity productEntity);

    void deleteById(Integer id);

    Optional<ProductEntity> findById(Integer id);

    List<ProductEntity> findAll();
}
