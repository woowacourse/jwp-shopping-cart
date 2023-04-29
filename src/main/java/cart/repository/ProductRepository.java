package cart.repository;

import java.util.List;
import java.util.Optional;

import cart.domain.Product;

public interface ProductRepository {

    Product save(Product product);

    Product update(Product product);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void deleteById(Long id);
}
