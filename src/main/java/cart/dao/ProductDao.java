package cart.dao;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product insert(Product product);

    void update(Product product);

    boolean isExist(Long id);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
