package cart.dao;

import cart.entity.ProductEntity;
import java.util.List;

public interface ProductDao {
    void insert(ProductEntity product);

    List<ProductEntity> selectAll();

    void update(ProductEntity product);
}
