package cart.catalog.dao;

import cart.catalog.domain.Product;
import java.util.List;

public interface CatalogDao {
    
    long insert(Product product);
    
    Product findByID(long id);
    
    Product findByName(String name);
    
    void deleteByID(long id);
    
    void update(Product product);
    
    List<Product> findAll();
}
