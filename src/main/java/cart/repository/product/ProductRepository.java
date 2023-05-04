package cart.repository.product;

import cart.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Long add(Product product);

    Long update(Product updateProduct);

    void delete(Product product);
}
