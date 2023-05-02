package cart.dao;

import java.util.List;

import cart.entity.Product;

public interface ProductDao {

    int insertProduct(Product product);

    List<Product> selectAllProducts();

    int updateProduct(Product product);

    int deleteProduct(int productId);
}
