package cart.domain.product;

import cart.dao.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Long insert(final ProductEntity productEntity);

    Optional<ProductEntity> findById(final Long id);

    List<ProductEntity> findAll();

    int update(final Long productId, final ProductEntity productEntity);

    int deleteById(final Long id);
}
