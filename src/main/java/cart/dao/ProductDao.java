package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {
    void save(ProductEntity productEntity);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void delete(long id);
}
