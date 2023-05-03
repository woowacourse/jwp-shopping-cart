package cart.dao;

import cart.domain.product.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Long insert(final Product product);

    List<Product> findAll();

    Optional<Product> findById(final long id);

    List<Product> findAllByIds(List<Long> productIds);

    void update(final Product product);

    void deleteById(final long id);
}
