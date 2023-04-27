package cart.repository;

import cart.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void add(Product product);

    void update(Product updateProduct);

    void delete(Product product);
}
