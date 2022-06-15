package woowacourse.shoppingcart.infra;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.domain.Product;

public interface ProductRepository {
    Product save(Product product);

    List<Product> findAllWithPage(int page, int size);

    Optional<Product> findById(long id);

    void deleteById(long id);

    long countAll();
}
