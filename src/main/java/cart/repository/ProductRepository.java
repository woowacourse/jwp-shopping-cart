package cart.repository;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    ProductEntity save(ProductEntity productEntity);

    Optional<ProductEntity> findById(Long id);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void deleteById(Long id);
}
