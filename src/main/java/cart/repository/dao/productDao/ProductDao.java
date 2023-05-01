package cart.repository.dao.productDao;

import cart.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Long save(final Product product);

    Optional<Product> findById(final Long id);

    List<Product> findAll();

    int update(final Product product);

    int delete(final Long id);
}
