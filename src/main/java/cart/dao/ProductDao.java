package cart.dao;

import cart.entity.Product;

import java.util.List;

public interface ProductDao {
    Product save(Product product);

    List<Product> findAll();

    Product update(Product product);

    void deleteById(long id);
}
