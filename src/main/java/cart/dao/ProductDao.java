package cart.dao;

import cart.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Long insert(final Product product);

    boolean isExist(final long id);

    Optional<Product> findById(final long id);

    List<Product> findAll();

    void update(final Product product);

    void deleteById(final long id);
}
