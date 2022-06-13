package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public interface ProductRepository {
    Long save(final Product product);

    Product findProductById(final Long productId);

    List<Product> findProducts();

    List<Product> findProducts(long size, long page);

    void delete(final Long productId);
}
