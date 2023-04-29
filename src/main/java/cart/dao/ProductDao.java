package cart.dao;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Long insert(final Product product);

    List<Product> findAll();

    Optional<Product> findById(final long id);

    void update(final Product product);

    void deleteById(final long id);
}
