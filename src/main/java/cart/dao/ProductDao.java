package cart.dao;

import cart.domain.product.Product;

import java.util.List;

public interface ProductDao {

    Long insert(final Product product);

    Product findById(final long id);

    List<Product> findAll();

    void update(final Product product);

    void deleteById(final long id);
}
