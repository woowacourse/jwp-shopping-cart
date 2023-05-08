package cart.product.dao;

import cart.product.domain.Product;

import java.util.List;

public interface ProductDao {
    Product findById(final Long id);
    
    List<Product> findAll();
    
    Long save(final Product product);
    
    void update(final Product product);
    
    void delete(final Long id);
}
