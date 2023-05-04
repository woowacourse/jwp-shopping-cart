package cart.dao;

import cart.domain.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    List<ProductEntity> selectAll();

    ProductEntity selectById(final long id);

    long insert(final ProductEntity productEntity);

    int update(final ProductEntity productEntity);

    int deleteById(final long id);
}
