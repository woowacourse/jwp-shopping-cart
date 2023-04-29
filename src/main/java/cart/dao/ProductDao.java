package cart.dao;

import cart.entity.ProductEntity;
import java.util.List;

public interface ProductDao {
    int insert(ProductEntity product);

    List<ProductEntity> selectAll();

    ProductEntity findById(int productId);

    void update(ProductEntity product);

    void delete(int productId);
}
