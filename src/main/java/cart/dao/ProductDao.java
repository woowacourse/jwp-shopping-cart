package cart.dao;

import cart.dao.entity.Product;

import java.util.List;

public interface ProductDao {

    Long save(final Product product);

    List<Product> findAll();

    int delete(final Long id);

    int update(final Long id, final Product product);
}
