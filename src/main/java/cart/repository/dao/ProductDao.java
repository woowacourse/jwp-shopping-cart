package cart.repository.dao;

import cart.repository.entity.ProductEntity;
import java.util.List;

public interface ProductDao {

    Long save(final ProductEntity productEntity);

    ProductEntity findById(final Long id);

    List<ProductEntity> findAll();

    int update(final ProductEntity productEntity);

    int delete(final Long id);
}
