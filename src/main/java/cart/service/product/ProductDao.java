package cart.service.product;

import cart.service.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Long save(Product product);

    Product update(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
