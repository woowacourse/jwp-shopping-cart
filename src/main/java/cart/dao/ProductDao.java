package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    List<ProductEntity> selectAll();

    void insert(final ProductEntity productEntity);
}
