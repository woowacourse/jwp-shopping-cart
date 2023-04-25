package cart.dao;

import java.util.Optional;

import cart.entiy.ProductEntity;

public interface ProductDao {

    ProductEntity save(ProductEntity productEntity);

    Optional<ProductEntity> findById(Long id);

    void deleteById(Long id);
}
