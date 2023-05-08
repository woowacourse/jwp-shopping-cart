package cart.persistence.dao;

import java.util.List;

import cart.persistence.entity.Product;

public interface ProductDao {

    long save(Product product);

    Product findByName(String name);

    List<Product> findAll();

    int update(Product product);

    int deleteById(long id);

    boolean existsById(long id);
}
