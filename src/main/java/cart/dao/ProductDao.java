package cart.dao;

import cart.entity.Product;

import java.util.List;

public interface ProductDao {
    void save(Product product);

    List<Product> findAll();

    void update(Product product);

    void delete(long id);
}
