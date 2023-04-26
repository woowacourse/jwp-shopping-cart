package cart.persistence.dao;

import cart.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    Long save(ProductEntity productEntity);

    ProductEntity findByName(String name);

    List<ProductEntity> findAll();

    void update(ProductEntity productEntity);

    void deleteById(long id);
}
