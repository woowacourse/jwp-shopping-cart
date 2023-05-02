package cart.dao;

import cart.domain.product.Product;

import java.util.List;

public interface ProductDao {

    Long insert(final Product product);

    List<Product> findAll();

    Product findById(final long id);

    void update(final Product product);

    void deleteById(final long id);
}
