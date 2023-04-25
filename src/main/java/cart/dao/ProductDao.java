package cart.dao;

import java.util.List;

import cart.dao.entity.Product;

public interface ProductDao {

    Long save(final Product product);

    List<Product> findAll();

    void delete(final Long id);

    void update(final Long id, final Product product);
}
