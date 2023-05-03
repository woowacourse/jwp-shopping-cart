package cart.repository;

import cart.domain.Product;
import cart.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    ProductEntity save(Product product);

    Optional<ProductEntity> findById(Long id);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void deleteById(Long id);
}
