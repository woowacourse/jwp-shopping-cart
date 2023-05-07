package cart.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Long save(Product product);

    void deleteById(Long id);

    Product update(Product product);

    Optional<Product> findById(Long productId);
}
