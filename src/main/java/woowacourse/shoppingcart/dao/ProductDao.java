package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

public interface ProductDao {
    Long save(Product product);

    Optional<ProductEntity> findById(long productId);

    List<ProductEntity> findAll();

    void delete(long productId);
}
