package cart.persistence.dao;

import java.util.List;

import cart.persistence.entity.ProductEntity;

public interface ProductDao {

    Long save(ProductEntity productEntity);

    ProductEntity findByName(String name);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void deleteById(long id);
}
