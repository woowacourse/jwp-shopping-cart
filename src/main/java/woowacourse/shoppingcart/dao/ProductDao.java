package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public interface ProductDao {
    Long save(Product product);

    Product findById(long productId);

    List<Product> findAll();

    void delete(long productId);
}
