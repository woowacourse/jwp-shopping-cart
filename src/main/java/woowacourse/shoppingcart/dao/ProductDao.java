package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public interface ProductDao {
    Long save(final Product product);

    Product findById(final Long productId);

    List<Product> findAll();

    void delete(final Long productId);
}
