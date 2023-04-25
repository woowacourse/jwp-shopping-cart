package cart.persistence;

import cart.domain.Product;

import java.util.List;

public interface ProductDao {

    void create(Product product);

    List<Product> findAll();

    void update(Product product);

    void delete(Long id);
}
