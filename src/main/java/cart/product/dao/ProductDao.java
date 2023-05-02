package cart.product.dao;

import cart.product.entity.ProductEntity;
import java.util.List;

public interface ProductDao {
    ProductEntity insert(ProductEntity product);

    List<ProductEntity> selectAll();

    ProductEntity findById(int productId);

    int update(ProductEntity product);

    int delete(int productId);
}
