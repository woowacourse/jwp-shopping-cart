package cart.dao.product;

import cart.domain.product.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    
    long insert(Product product);
    
    Optional<Product> findByID(long id);
    
    void deleteByID(long id);
    
    void update(Product product);
    
    List<Product> findAll();
}
