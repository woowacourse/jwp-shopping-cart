package cart.dao;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void update(Product product);

    void deleteById(Long id);

    List<Product> findByIds(List<Long> productIds);
}
