package cart.business;

import cart.business.domain.Product;

import java.util.List;
import java.util.Optional;

// TODO: OPTIONAL 응용
public interface ProductRepository {

    Integer insert(Product product);

    Optional<Product> findByName(String name);

    List<Product> findAll();

    Product update(Product product);

    Product remove(Integer productId);

}
