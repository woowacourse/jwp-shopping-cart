package cart.product.dao;

import cart.product.domain.Product;
import java.util.List;

public interface ProductDao {
    
    long insert(Product product);
    
    Product findByID(long id);
    
    Product findByName(String name);
    
    void deleteByID(long id);
    
    void update(Product product);
    
    List<Product> findAll();
}
