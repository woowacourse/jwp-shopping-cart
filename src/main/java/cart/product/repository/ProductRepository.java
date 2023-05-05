package cart.product.repository;

import cart.product.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    void update(Product product);

    Product findById(long id);

    void deleteById(long id);
}
