package cart.dao;

import cart.domain.Product;

import java.util.List;

public interface ProductDao {

    Long insert(final Product product);
    
    List<Product> findAll();
}
