package cart.dao;

import cart.domain.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> selectAll();

    Product selectById(final long id);

    long insert(final Product product);

    int update(final Product product);

    int deleteById(final long id);
}
