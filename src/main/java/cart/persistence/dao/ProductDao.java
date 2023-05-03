package cart.persistence.dao;

import cart.domain.Product;

import java.util.List;

public interface ProductDao {

    Long insertAndGetKeyHolder(Product product);

    List<Product> findAll();

    void update(Product product);

    void delete(Long id);

    Product findById(Long id);
}
