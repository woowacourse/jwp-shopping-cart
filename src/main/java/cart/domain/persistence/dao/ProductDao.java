package cart.domain.persistence.dao;

import java.util.List;

import cart.domain.persistence.entity.ProductEntity;

public interface ProductDao {

    long save(ProductEntity productEntity);

    ProductEntity findByName(String name);

    List<ProductEntity> findAll();

    int update(ProductEntity productEntity);

    int deleteById(long id);

    boolean existsById(long id);
}
