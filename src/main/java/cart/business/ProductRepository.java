package cart.business;

import cart.business.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Integer insert(Product product);

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    Product update(Product product);

    Product remove(Integer productId);
}
