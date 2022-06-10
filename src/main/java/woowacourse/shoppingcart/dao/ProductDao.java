package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

public interface ProductDao {
    Long save(Product product);

    ProductEntity findById(long productId);

    List<ProductEntity> findAll();

    void delete(long productId);
}
