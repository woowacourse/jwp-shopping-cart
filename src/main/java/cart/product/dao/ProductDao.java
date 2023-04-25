package cart.product.dao;

import cart.product.domain.Product;
import java.util.List;

public interface ProductDao {
    
    void insert(Product product);
    
    Product findByID(long id);
    
    List<Product> findAll();
}
