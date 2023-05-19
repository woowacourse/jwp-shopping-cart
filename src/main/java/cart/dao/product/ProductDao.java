package cart.dao.product;

import cart.domain.Product;

import java.util.List;

public interface ProductDao {

    Long save(Product product);

    List<Product> findAll();

    Product findById(Long id);

    void deleteById(Long id);

    void updateById(Long id, Product product);
}
