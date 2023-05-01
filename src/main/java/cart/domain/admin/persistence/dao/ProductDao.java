package cart.domain.admin.persistence.dao;

import java.util.List;

import cart.domain.admin.persistence.entity.ProductEntity;

public interface ProductDao {

    Long save(ProductEntity productEntity);

    ProductEntity findByName(String name);

    List<ProductEntity> findAll();

    int update(ProductEntity productEntity);

    int deleteById(long id);
}
