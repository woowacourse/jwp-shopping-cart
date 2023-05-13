package cart.persistence;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductRepository {

    Integer insert(ProductEntity product);

    List<ProductEntity> findAll();

    Integer update(Integer id, ProductEntity product);

    Integer remove(Integer id);

    void findSameProductExist(ProductEntity product);
}
