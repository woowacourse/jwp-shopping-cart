package cart.repository;

import cart.domain.Product;
import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {
    ProductEntity save(Product product);

    ProductEntity update(ProductEntity entity);

    ProductEntity findById(Long id);

    List<ProductEntity> findAll();

    void deleteById(Long id);
}
