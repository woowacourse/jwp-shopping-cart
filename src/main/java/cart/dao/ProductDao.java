package cart.dao;

import cart.domain.Product;
import cart.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<ProductEntity> save(Product product);

    ProductEntity update(ProductEntity entity);

    Optional<ProductEntity> findById(Long id);

    List<ProductEntity> findAll();

    void deleteById(Long id);
}
