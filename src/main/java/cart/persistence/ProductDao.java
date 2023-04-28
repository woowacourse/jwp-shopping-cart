package cart.persistence;

import cart.domain.Product;

import java.util.List;

public interface ProductDao {

//    void create(Product product);
    Long create(Product product);

    Product find(Long id);

    List<Product> findAll();

    Long update(Product product);

    void delete(Long id);
}
