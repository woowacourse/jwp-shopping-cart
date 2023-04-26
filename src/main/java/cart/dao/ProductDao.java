package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {
    Integer insert(ProductEntity productEntity);

    void update(ProductEntity productEntity);

    void deleteById(Integer id);

    ProductEntity select(Integer id);

    List<ProductEntity> findAll();
}
