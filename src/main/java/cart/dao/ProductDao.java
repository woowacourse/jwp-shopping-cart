package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    List<ProductEntity> selectAll();

    void insert(final ProductEntity productEntity);

    void updateById(final Long id, final ProductEntity productEntity);

    void deleteById(final Long id);
}
