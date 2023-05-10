package cart.product.dao;

import cart.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product save(Product product);

    int update(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    int deleteById(Long id);
}
