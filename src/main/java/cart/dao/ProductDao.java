package cart.dao;

import cart.dao.entity.Product;
import java.util.List;

public interface ProductDao {

    Long save(final Product product);

    List<Product> findAll();

    void delete(final Long id);

    void update(final Long id, final Product product);

    boolean existBy(final Long id);
}
