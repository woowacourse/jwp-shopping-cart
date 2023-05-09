package cart.product.dao;

import cart.product.domain.Product;

import java.util.List;

public interface ProductDao {

    void insert(final Product product);

    Product findById(final Long id);

    List<Product> findAll();

    void updateById(final Long id, final Product product);

    void deleteById(final Long id);
}
