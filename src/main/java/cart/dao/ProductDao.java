package cart.dao;

import cart.dto.ProductRequestDto;
import cart.entity.Product;
import java.util.List;

public interface ProductDao {

    public Long insertProduct(Product product);
    public List<Product> findAll();
    public Product findById(Long id);
    public Product updateProduct(Long id, ProductRequestDto productRequestDto);
    public Long deleteProduct(Long id);
}
