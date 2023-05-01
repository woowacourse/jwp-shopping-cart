package cart.persistence;

import cart.domain.product.Product;

import java.util.List;

public interface ProductDao {

    Long create(Product product);

    Product find(Long id);

    List<Product> findAll();

    Long update(Product product);

    void delete(Long id);
}
