package cart.entity.dao;

import cart.entity.Product;

import java.util.List;

public interface ProductDao {
    Product save(Product product);

    List<Product> findAll();

    Product findById(long id);

    Product update(Product product);

    void deleteById(long id);
}
