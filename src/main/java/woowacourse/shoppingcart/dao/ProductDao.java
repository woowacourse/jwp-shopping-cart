package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public interface ProductDao {
    Long save(final Product product);

    Product findProductById(final Long productId);

    List<Product> findProducts();

    void delete(final Long productId);
}
