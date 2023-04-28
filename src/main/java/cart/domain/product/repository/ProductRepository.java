package cart.domain.product.repository;

import cart.domain.product.entity.Product;
import java.util.List;

public interface ProductRepository {

    Product save(final Product product);

    List<Product> findAll();

    int update(final Product product);
}
