package cart.dao;

import cart.entity.Product;

import java.util.List;

public interface ProductDao {

    int insert(Product product);

    List<Product> selectAll();

    int update(Product product);

    int delete(int productId);
}
