package cart.dao;

import cart.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> selectAll();

    Optional<Product> selectById(final long id);

    long insert(final Product product);

    int update(final Product product);

    int deleteById(final long id);
}
