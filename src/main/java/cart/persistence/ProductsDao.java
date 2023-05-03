package cart.persistence;

import cart.domain.product.Product;

import java.util.List;

public interface ProductsDao {

    Long create(Product product);

    Product findById(Long id);

    List<Product> findAll();

    Long update(Product product);

    void delete(Long id);
}
