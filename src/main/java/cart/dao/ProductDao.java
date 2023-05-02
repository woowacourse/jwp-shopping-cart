package cart.dao;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    public Long insertProduct(Product product);

    public List<Product> findAll();

    public Optional<Product> findById(Long id);

    public void updateProduct(Long id, Product product);

    public Long deleteProduct(Long id);
}
