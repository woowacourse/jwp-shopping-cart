package cart.dao;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<Long> saveAndGetId(final Product product);

    List<Product> findAll();

    void update(final Product product);

    void delete(final Long id);

    Optional<Product> findById(final Long id);
}
