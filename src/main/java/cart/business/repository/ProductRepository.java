package cart.business.repository;

import cart.business.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Integer insert(Product product);

    Optional<Product> findById(Integer productId);

    Optional<Product> findByName(String name);

    List<Product> findAll();

    Product update(Product product);

    Product remove(Integer productId);
}
