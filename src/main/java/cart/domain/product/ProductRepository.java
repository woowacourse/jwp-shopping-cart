package cart.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Long insert(final Product product);

    Optional<Product> findById(final Long id);

    List<Product> findAll();

    int update(final Long productId, final Product product);

    int deleteById(final Long id);
}
