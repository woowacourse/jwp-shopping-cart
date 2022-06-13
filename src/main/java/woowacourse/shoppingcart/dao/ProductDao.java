package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

public interface ProductDao {
    Long save(final Product product);

    ProductEntity findProductById(final Long productId);

    List<ProductEntity> findProducts();

    void delete(final Long productId);
}
