package cart.domain.product;

import java.util.List;

public interface ProductRepository {
    
    long save(Product product);

    List<Product> findAll();

    void update(Product productToUpdate);

    void deleteById(long id);
}
