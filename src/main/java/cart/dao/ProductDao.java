package cart.dao;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(long id);

    Product update(Product product);

    void deleteById(long id);
}
