package cart.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    long save(Product product);

    List<Product> findAll();

    void update(Product productToUpdate);

    void deleteById(long id);

    Optional<Product> findById(Long id);
}
