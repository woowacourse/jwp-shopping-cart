package cart.service.product;

import cart.service.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    long save(Product product);

    Product update(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll();

    void deleteById(long id);
}
